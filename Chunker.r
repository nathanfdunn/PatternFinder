

timeIndexFinder = function(frame){
	#Check which are sorted
}


csvToFrame = function(fileName, timeInd = NA, flipTimes = T){
	fileName = paste("../DataSets_R/",fileName,sep="")
	myframe <<- read.csv(fileName)		#TODO - make not global
	if (is.na(timeInd)){
		timeInd = timeIndexFinder(myframe)
	}
	if (flipTimes){
		#Do stuff to flip the times so it will be in order
		#How to tell if time metric is negative or positive?
	}	
	#Possibly do stuff to make the names more manageable
	return(myframe)
}

#Produces a list of pairs delimiting the time window for a chunk
partitioner = function(times, numChunks #, chunkWidth, startTime = times[1]
	){
	posts = vector("list", numChunks)
	inds = vector("list", numChunks)
	eps = 0.0001
	partitionPoints <<- seq(min(times), max(times), len = numChunks+1)
	partitionPoints[1] = partitionPoints[1] - eps
	partitionPoints[length(partitionPoints)] = partitionPoints[length(partitionPoints)] + eps
	for (i in 1:numChunks){
		posts[[i]] = c(partitionPoints[i], partitionPoints[i+1])
		inds[[i]] = which( partitionPoints[i] < times & times < partitionPoints[i+1])
	}
	out = list()
	out[["Posts"]] = posts
	out[["Inds"]] = inds
	return(out)
}


tableChunker = function(frame, timeInd, numChunks, desiredQuantIds){

	times <<- frame[[timeInd]]
	partition = partitioner(times, numChunks)
	out = list()
	#Bookkeeping stuff
	#out[["Partition"]] = partition
	#out[["RawTimes"]] = times
	#out[["Quants"]]	= desiredQuantIds
	out[["NumChunks"]] = numChunks
	timeChunks = vector("list", numChunks)
	for (i in 1:numChunks) timeChunks[[i]] = times[ partition[["Inds"]] [[i]] ]
	out[["TimeChunks"]] = timeChunks
	
	for (quant in desiredQuantIds){
		chunks = vector("list", numChunks)
		for (i in 1:numChunks) chunks[[i]] = frame[[quant]][ partition[["Inds"]] [[i]] ]
		out[[quant]] = chunks
	}
	
	return(out)
}

csvChunker = function(fileName, timeInd, desiredQuantIds, flipTimes = T, 
		numChunks = 10){	#NA, chunkWidth = NA){
	
	frame <<- csvToFrame(fileName, timeInd, flipTimes)
	# time = frame[[timeInd]]			#For sake of following routine
	# if (!is.na(chunkWidth) && !is.na(numChunks))
		# stop("Error: cannot specify both chunkWidth and numChunks at this time")
	# if (is.na(chunkWidth) && is.na(numChunks))
		# numChunks = 10
	# if (is.na(numChunks))
		# numChunks = round( (time[length(time)] - time[1]) / chunkWidth)
	# if (is.na(chunkWidth))
		# chunkWidth = diff(range(time))/numChunks
		
	chunks <<- tableChunker(frame, timeInd, numChunks, desiredQuantIds)
	return(chunks)
}

fileName = "nfdunn_GISP2.csv"
timeInd = 14
quants = c("Ca..ug.L.")#, "k..null.", "no3..null.")

blah = csvChunker(fileName, timeInd, quants, numChunks = 20)







# createChunkObj = function(vals, index, times = 1:length(vals), userEval = T){
	# out = list()
	# if (userEval)
		# out[["classification"]] = userClassification(vals, times)
	# else
		# out[["classification"]] = NULL
	# out[["rawVals"]]  = vals
	# out[["rawTimes"]] = times
	# out[["timeWindow"]] = NULL
	# out[["index"]]	  = index
	# return(out)
# }

# validTypes = c("SPI, DIP, DEC, INC, FLA, UNK")

# userClassification = function( vals, times ){
	# maxY = max(vals)
	# minY = min(min(vals), 0)
	# plot( times, vals, ylim = c(minY, maxY) )
	
	# type = -1
	# while (!(type %in% validTypes) && (type == "q")){
		# cat("What best describes this?\n")
		# cat("Valid Types:")
		# cat(validTypes)
		# type = readline()
		# cat("\n\n")
	# }
	# if (type == "q") type = "abort"
	# return(type)
# }





