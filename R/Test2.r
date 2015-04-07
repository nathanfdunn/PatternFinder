library(moments)


types =(c("FLA", "SPI", "DIP", "INC", "DEC"))


flat = function(n=100, a = 10, b = 0.1){
	list(runif(n)*a + b,("FLA"))
}

spike = function(n=100, a = 10, b = 0.1){
	out = flat(n,a,b)[[1]]
	out [10:20] = out[10:20] + a^2.5*rexp(11, a)
	list(out,("SPI"))
}

dip = function(n=100,a=10,b=0.1){
	out = -spike(n,a,b)[[1]]
	out = out - min(out) + b
	list(out,("DIP"))
}

inc = function(n=100, a=10, b=0.1){
	out = flat(n,a,b)[[1]]
	out = out + seq(-a, a, len=n) #* runif(n)
	list(out,("INC"))
}

dec = function(n=100,a=10,b=0.1){
	out = -inc(n,a,b)[[1]]
	out = out - min(out) + b
	list(out,("DEC"))
}

getCol = function(ind){
	cols = c("red", "blue", "orange", "purple", "green")
	ind = match(ind, types)
	return(cols[ind])
	#return(c("red","blue", "orange", "purple", "green", "black", "gray")[ind+3])
}


randFeature = function(varied = F){
	n = round(runif(1,30,200))
	a = rexp(1,10)
	b = exp(rnorm(1))
	if(!varied){n=100; a=10; b=0.1}
	
	sample(c(flat, spike, dip, inc, dec), 1)[[1]](n,a,b)
}


growth = function(chunk, time){
	fit = lm(chunk~time)
	endPoints = fit[[1]][2]*c(time[1],time[length(time)]) + fit[[1]][1]
	growth = endPoints[2]/endPoints[1]
	#return(log(growth))
}

featureExtractor = function(chunk, times = 1:length(chunk)){	
	out = NULL
	# out["npskew"] = (mean(chunk) - median(chunk))/sd(chunk)
	# out["myskew"] = (median(chunk)-mean(chunk))/mean(chunk)
	out["sk"] = skewness(chunk)
	out["minZ"] = min(scale(chunk))
	out["maxZ"] = max(scale(chunk))
	out["r"] = cor(times, chunk)
	out["cv"] = sd(chunk)/mean(chunk)
	# out["ku"] = kurtosis(chunk)
	out["growth"] = growth(chunk,times)
	#out["type"] = type

 	#out["bogo"] = runif(1)
	#out["bogo2"]= runif(1)
	return(out)
}

featureExtractor.len = length(featureExtractor(runif(100)))
featureExtractor.names = names(featureExtractor(runif(100)))


newFrame = function(num=100, varied=F){	
	mat = matrix(0, num, featureExtractor.len)
	typeVec = rep(0,num)
	cols = rep("", num)
	for (i in 1:num){
		feat = randFeature(varied)
		chunk = feat[[1]]
		type = feat[[2]]
		mat[i,] = featureExtractor(chunk)
		cols[i] = getCol(type)
		#print(cols[i])
		#print(type)
		typeVec[i] = type
	}
	
	frame = data.frame(mat)
	#print(frame)
	colnames(frame) = featureExtractor.names
	frame$type = factor(typeVec)
	return(list(frame, cols))
}

frame =newFrame(100, T)
cols = frame[[2]]
frame = frame[[1]]

#summary(frame)
pairs(frame, col=cols)
#readline("Read")

model = svm(frame$type ~ ., frame)

frame2 = newFrame(1000,T)
cols2 = frame2[[2]]
frame2 = frame2[[1]]

#pairs(frame2[names(frame2)!="type"], col=cols2)
pairs(frame2, col=cols2)
#readline()
#res = predict(model, newdata=frame2)
#res = predict(model, newdata=featureExtractor(randFeature()[1]))

# print(table(res, frame2$type))


# classify = function(chunk){
	# ylim=c(min(0,min(chunk)), max(chunk))
	# plot(chunk,ylim=ylim,type='l')
	# fe <<- featureExtractor(chunk)
	# fr <<- data.frame( as.list(fe))
	# out <<- predict(model, newdata = fr)
	# print(out)
# }


# for (i in 1:10){
	# feat = randFeature(T)
	# print("Actual Type:")
	# print(feat[[2]])
	# classify(feat[[1]])
	# readline("Pause")
# }




#types = frame$type
#frame$type = NULLi

#x=knn()


# # blah = randFeature()
# chunk = blah[[1]]
# feat = featureExtractor(chunk)
# typeblah = blah[[2]]
# miniframe = t(data.frame(feat))
# miniframe$type = typeblah



#plot(datSet)







