

library(e1071)

growth = function(chunk, time){
	fit = lm(chunk~time)
	endPoints = fit[[1]][2]*c(time[1],time[length(time)]) + fit[[1]][1]
	growth = endPoints[2]/endPoints[1]
	#return(log(growth))
}

featureExtractor = function(chunk, times = 1:length(chunk)){	
	mychunk <<- chunk
	mytimes <<- times
	#print(chunk)
	#print(typeof(chunk))
	#print(typeof(times))
	out = NULL
	#out["npskew"] = (mean(chunk) - median(chunk))/sd(chunk)
	#out["myskew"] = (median(chunk)-mean(chunk))/mean(chunk)
	out["sk"] = skewness(chunk)
	#out["minZ"] = min(scale(chunk))
	#out["maxZ"] = max(scale(chunk))
	out["r"] = cor(times, chunk)
	out["cv"] = sd(chunk)/mean(chunk)
	#out["ku"] = kurtosis(chunk)
	out["growth"] = growth(chunk,times)
	#out["type"] = type

 	#out["bogo"] = runif(1)
	#out["bogo2"]= runif(1)
	return(out)
}

featureExtractor.len = length(featureExtractor(runif(100)))
featureExtractor.names = names(featureExtractor(runif(100)))


featureVecsToFrame = function(listOfFeatureVecs){
	nrows = length(listOfFeatureVecs)
	ncols = length(listOfFeatureVecs[[1]])
	mat = matrix(0, nrows, ncols)
	for (i in 1:nrows){
		mat[i,] = listOfFeatureVecs[[i]]
	}
	myframe = data.frame(mat)
	colnames(myframe) = featureExtractor.names
	return(myframe)
}


getCol = function(type){
	if (type == "FLA") return("blue")
	if (type == "SPI") return("red")
	if (type == "DEC") return("green")
	if (type == "INC") return("purple")
	#if (type == "DIP")
	return("orange")
}

#dev.new()

#classified chunks
buildModel = function(cc){
	cc <<- cc
	numChunks = cc[["NumChunks"]]
	quants = cc[["Quants"]]
	classifiedChunks = list()
	#classifications = list()
	classifications = NULL
	features = list()
	
	cols = NULL
	
	for (quant in quants){
		for (i in 1:numChunks){
			chunk = cc[[quant]][[i]]
			if ("classification" %in% names(chunk)){
				if (chunk[["classification"]] != "UNK"){
					cols = c(cols, getCol(chunk[["classification"]]))
					
					classifiedChunks = c(classifiedChunks, chunk[["rawVals"]])
					classifications = c(classifications, chunk[["classification"]])
					mychunk <<- chunk
					#print(chunk[["rawVals"]])
					#myfeat <<- featureExtractor(chunk[["rawVals"]])
					#features <<- features
					features = c(features, list(featureExtractor(chunk[["rawVals"]])))#, chunk[["rawTimes"]]))
				}
			}
		}
	}
	features <<- features
	myframe = featureVecsToFrame(features)	
	
	myframe$type = factor(classifications)
	
	dev.set(which=dev.next())
	pairs(myframe, col = cols)
#	frame$type = factors(frame$type)
	myframe <<- myframe
	
	model = svm(myframe$type ~ ., myframe)
	
	return(model)
}


myPredict = function(model, valChunk, timeChunk){
	featureVec = featureExtractor(valChunk, timeChunk)
	myframe <<- data.frame(as.list(featureVec))
	return(predict(model, newdata = myframe))
}


# verify = function( train, test ){
	# #train2 = list()
	# #train2[["Quants"]]
	
	# model = buildModel(train)
	# out = list()#vector("list", length(test))
	# for (chunk in test[["Ca..ug.L."]]){
		# if ("classification" %in% names(chunk)){
			# pred <<- myPredict(chunk[["rawVals"]], chunk[["rawTimes"]])
			# #out = c(out, myPredict(chunk[["rawVals"]], chunk[["rawTimes"]]))
			# print(pred)
		# }
	# }
	# return(out)
# }

