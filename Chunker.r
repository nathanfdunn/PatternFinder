

timeIndexFinder = function(myframe){
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


tableChunker = function(myframe, timeInd, numChunks, desiredQuantIds){

	times <<- myframe[[timeInd]]
	partition = partitioner(times, numChunks)
	out = list()
	#Bookkeeping stuff
	out[["Partition"]] = partition
	#out[["RawTimes"]] = times
	out[["Quants"]]	= desiredQuantIds
	out[["NumChunks"]] = numChunks
	timeChunks = vector("list", numChunks)
	for (i in 1:numChunks) timeChunks[[i]] = times[ partition[["Inds"]] [[i]] ]
	out[["TimeChunks"]] = timeChunks
	
	for (quant in desiredQuantIds){
		chunks = vector("list", numChunks)
		for (i in 1:numChunks) chunks[[i]] = myframe[[quant]][ partition[["Inds"]] [[i]] ]
		out[[quant]] = chunks
	}
	
	return(out)
}

csvChunker = function(fileName, timeInd, desiredQuantIds, flipTimes = T, 
		numChunks = 10){	#NA, chunkWidth = NA){
	
	myframe <<- csvToFrame(fileName, timeInd, flipTimes)
	# time = frame[[timeInd]]			#For sake of following routine
	# if (!is.na(chunkWidth) && !is.na(numChunks))
		# stop("Error: cannot specify both chunkWidth and numChunks at this time")
	# if (is.na(chunkWidth) && is.na(numChunks))
		# numChunks = 10
	# if (is.na(numChunks))
		# numChunks = round( (time[length(time)] - time[1]) / chunkWidth)
	# if (is.na(chunkWidth))
		# chunkWidth = diff(range(time))/numChunks
	out = list()
	out[["CoreName"]] = fileName	
	chunks <<- tableChunker(myframe, timeInd, numChunks, desiredQuantIds)
	#chunks[["CoreName"]] = fileName
	out = c(out, chunks)
	return(out)
}

source("Classifier.r")

realTimePrediction = function(chunkList, valChunk, timeChunk){
	model = buildModel(chunkList)
	return(myPredict(model, valChunk, timeChunk))
}


classifyChunkList = function(chunkList){
	quants = chunkList[["Quants"]]
	numChunks = chunkList[["NumChunks"]]
	for (quant in quants){
		if (numChunks < 1) return(chunkList)
		for (i in 1:numChunks){
			timeChunk = chunkList[["TimeChunks"]][[i]]
			valChunk = chunkList[[quant]][[i]]
			
			if (i > 5){
				cat("This is out best guess\n")
				#print(chunkList)
				#print(valChunk)
				#print(timeChunk)
				print(realTimePrediction(chunkList, valChunk, timeChunk))
			}
			classification = classifyChunk(valChunk, timeChunk)
			
			if (classification[["classification"]] == "q" ||
					 classification[["classification"]] == "Q")
				return(chunkList)
			
			chunkList[[quant]][[i]] = classification
		}
	}
	return(chunkList)
}




# classifyChunkList = function(chunkList){
	# quants = chunkList[["Quants"]]
	# numChunks = chunkList[["NumChunks"]]
	# for (quant in quants){
		# if (numChunks < 1) return(chunkList)
		# backFlag = F
		# i = 1
		# while (i <= numChunks){
		# #for (i in 1:numChunks){
			# timeChunk = chunkList[["TimeChunks"]][[i]]
			# valChunk = chunkList[[quant]][[i]]
			# if (backFlag) {valChunk = valChunk[["rawVals"]]}
			# classification = classifyChunk(valChunk, timeChunk)
			
			# if (classification[["classification"]] == "q"){
				# return(chunkList)
			# }else if(classification[["classification"]] == "back"){
				# if (i == 1)
					# i = i - 1
				# else{
					# i = i - 2
					# backFlag = T
				# }
			# }else{
				# chunkList[[quant]][[i]] = classification
				# backFlag = F
			# }
			# i = i+1
			# print(i)
			# #chunkList[[quant]][[i]] = classifyChunk(valChunk, timeChunk)
		# }
	# }
	# return(chunkList)
# }


classifyChunk = function(vals, times# = 1:length(vals), index
			#,userEval = T
			){
	out = list()
	# if (userEval)
	out[["classification"]] = userClassification(vals, times)
	# else
		# out[["classification"]] = NULL
	out[["rawVals"]]  = vals
	out[["rawTimes"]] = times		#TODO
	#out[["timeWindow"]] = NULL		#see partition
	#out[["index"]]	  = index
	return(out)
}

validTypes = c("SPI", "DIP", "DEC", "INC", "FLA", "UNK")
controlSeq = c("q", "Q")#, "back")

userClassification = function( vals, times ){
	maxY = max(vals)
	minY = min(min(vals), 0)
	dev.set(which=dev.next())
	plot( times,(vals), ylim = c(minY, maxY), type='l' )
	#print("good1")
	type = -1
	while (!(type %in% validTypes) && !(type %in% controlSeq)){
		cat("What best describes this?\n")
		cat("Valid Types: ")
		cat(validTypes)
		#print("good2")
		type = readline()
		#print(type)
		#print( type %in% validTypes)
		#print("good3")
		cat("\n\n")
	}
	#if (type == "q") type = "abort"
	return(type)
}







# fileName = "nfdunn_GISP2.csv"
# timeInd = 14
# quants = c("Ca..ug.L.")#, "k..null.", "no3..null.")

#chunksTest = csvChunker(fileName, timeInd, quants, numChunks = 100)
#classifyTest = classifyChunkList(chunksTest)


blah = csvChunker(fileName, timeInd, quants, numChunks = 100)
blah2 = classifyChunkList(blah)



