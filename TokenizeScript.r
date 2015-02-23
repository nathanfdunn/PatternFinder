
source("Tokenizer2.r")


#Path prefix CSVs found in
rawPath = "../DataSets_R/"

#Path to java project where text files go
resPath = "PatternDetection2/"




parallelTokenize = function(rawFileName, timeInd, quantIDInds = -timeInd, 
		numChunks = NA, chunkWidth = NA, outFileName = rawFileName){
		
	rawFileName = paste(rawPath, rawFileName, ".csv", sep="")
	outFileName = paste(resPath, outFileName, ".txt", sep="")
	
	table = read.csv(rawFileName)
	times = table[[timeInd]]
	quantNames = colnames(table)[quantIDInds]
	
	if (!is.na(chunkWidth) && !is.na(numChunks))
		stop("Error: cannot specify both chunkWidth and numChunks at this time")
	if (is.na(chunkWidth) && is.na(numChunks))
		numChunks = 100
	if (is.na(numChunks))
		numChunks = round( diff(range(times)) / chunkWidth)
	if (is.na(chunkWidth))
		intervalWidth = diff(range(times))/numChunks
	
	quantChunks = list()
	quantChunks[["time"]] = splitter(times, times, chunkWidth, numChunks)[[2]]		#time info unneeded?
	for (id in quantNames){
		quantChunks[[id]] = splitter(table[[id]], times, chunkWidth, numChunks)[[1]]
	}
	
	quantTokens = list()
	for (id in quantNames){
		quantTokens[[id]] = tokenize(quantChunks[[id]], quantChunks[["time"]], chunkWidth, numChunks)
	}
	
	write(paste(c("Time", quantNames), collapse="\t"), file=outFileName)
	n=length(quantNames)
	for (i in length(quantTokens[[1]])){
		vals = rep(0, n+1)
		vals[1] = i
		for (j in 1:n){
			vals[j+1] = quantTokens[[j]][i]
		}
		write(paste(vals, collapse="\t"), file=outFileName)
	}
	
	return( quantChunks )
}









