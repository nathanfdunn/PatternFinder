#source("Tokenizer.r")
source("Tokenizer.r")
if (!exists("mynewvals")){
	source("FakeVals.r")
}

#margins=par()$mar
#margins[1]=10
#par("mar"=margins)

margins=c(10,4.1,4.1,2.1)
par("mar"=margins)

spiX = c(-0.5, 0, 0, 0.5)
spiY = c(-0.5, 0.5, 0.5, -0.5)

dipX = spiX
dipY = -spiY

flatX = c(-0.5, 0.5)
flatY = c(0, 0)

incX = c(-0.5, 0.5)
incY = c(-0.5, 0.5)

decX = incX
decY = -incY

nullX = c(-0.5, 0, 0, 0.5, 0.5, 0)
nullY = c(0, 0.5, 0.5, 0, 0, -0.5)


drawToken=function(tokenType, yOff=0, xOff, scale=c(1,1)){
	# print(tokenType)			#TODO
	# tokenType=min(c(tokenType, -2))
	xVerts = 0; yVerts = 0
	if (tokenType == DIP){
		xVerts = dipX
		yVerts = dipY
	}else if(tokenType == SPIKE){
		xVerts = spiX
		yVerts = spiY
	}else if(tokenType == FLAT){
		xVerts = flatX
		yVerts = flatY
	}else if(tokenType == INC){
		xVerts = incX
		yVerts = incY
	}else if(tokenType == DEC){
		xVerts = decX
		yVerts = decY
	}else{
		xVerts = nullX
		yVerts = nullY
		print("???")
		xVerts = decX
		yVerts = incY		#TODO
	}
	
	# else if(tokenType == NULL_){ 
		# print("?")			#TODO
		# xVerts = decX
		# yVerts = decY
		# #xVerts = nullX
		# #yVerts = nullY
	# }
	xVerts = xVerts * scale[1] + xOff
	yVerts = yVerts * scale[2] + yOff
	for (i in seq(1,length(xVerts),2)){
		segments(xVerts[i], yVerts[i], xVerts[i+1], yVerts[i+1])
	}
	
}

scaleConst=.358
xScaleConst = 100


myPlot2 = function( dat, times=1:length(dat), startTime=1, color='blue', chunkWidth = NA, numChunks = NA){
	
	
	if (!is.na(chunkWidth) && !is.na(numChunks))
		stop("Error: cannot specify both chunkWidth and numChunks at this time")
	if (is.na(chunkWidth) && is.na(numChunks))
		numChunks = 10
	if (is.na(numChunks))
		numChunks = round( (times[length(times)] - times[1]) / chunkWidth)
	if (is.na(chunkWidth))
		chunkWidth = diff(range(times))/numChunks
		
	myMin = min(dat)
	myMax = max(dat)
	myRange = myMax - myMin
	myScale = myRange/scaleConst
	
	myDat <<- dat
	myTimes <<- times

	plot(times, dat, xlab="", ylab="", type='l', col=color)
	#plot(times, mynewvals, xlab="", ylab="", type='l', col=color)
	par(xpd=F)

	tokens = tokenize(dat, times, chunkWidth)
	print(tokens)
	timeChunks = splitter(dat, times, chunkWidth)[[2]]
	
	numTokes = length(timeChunks)
	
	t1=timeChunks[[1]][1]
	par(xpd=F)
	abline(v=t1)
	par(xpd=T)
	segments(t1, myMin -.22*myScale, t1, myMin-.1*myScale)

	for (i in 1:numTokes){
		time = timeChunks[[i]]
		myXScale = diff(range(time))/xScaleConst
		#myXScale = (time[length(time)] - time[1])/xScaleConst
		t1 = time[length(time)]
		par(xpd=F)
		abline(v=t1)
		par(xpd=T)
		segments(t1, myMin -.22*myScale, t1, myMin-.1*myScale)
		drawToken(tokens[i], yOff=myMin -0.16*myScale, xOff = t1-50*myXScale, scale = c(50*myXScale, myScale * 1/15) )
	}
	return(0)
}

# times = c( seq(1, 50, len=100), seq(51, 300, len=100), seq(301, 400, len=100), seq(401, 410, len=100), seq(411, 1000, len=100), seq(1001, 1050, len=100) )/1050*600

# # myPlot2(mynewvals, times)
# # readline()
# myPlot2(mynewvals)
# readline()
# mynewvals2=mynewvals
# mynewvals2[360]=NA
# myPlot2(mynewvals2)
# #myPlot2(mynewvals)