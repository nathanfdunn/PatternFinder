#Tokens:
DIP		="DIP"
DEC		="DEC"
FLAT	="FLAT"
INC		="INC"
SPIKE	="SPIKE"
NULL_ 	="NULL_"

tokToChar=function(val){
	if (val==DIP){return("v")}
	if (val==DEC){return('\\')}
	if (val==FLAT){return("-")}
	if (val==INC){return("/")}
	if (val==SPIKE){return("^")}
	if (val==NULL_){return("?")}
	stop("Unrecognized Token Type")
}



charInterp=function(tokens){
	out=""
	for(t in tokens){
		out=cat(out,tokToChar(t))
	}
	
	return(out)
}


tokenize = function( dat, time=1:length(dat), chunkWidth = NA, numChunks = NA){		#width=100 ){
	
	if (!is.na(chunkWidth) && !is.na(numChunks))
		stop("Error: cannot specify both chunkWidth and numChunks at this time")
	if (is.na(chunkWidth) && is.na(numChunks))
		numChunks = 10
	if (is.na(numChunks))
		numChunks = round( (time[length(time)] - time[1]) / chunkWidth)
	if (is.na(chunkWidth))
		chunkWidth = diff(range(time))/numChunks
	
	chunks = splitter(dat, time, chunkWidth)
	datChunks = chunks[[1]]
	timeChunks = chunks[[2]]
	n=length(datChunks)
	out = rep(NA, n) #vector("list", n)
	for (i in 1:n){
		out[i] = classifier(datChunks[[i]], timeChunks[[i]])
	}
	return(out)
}



splitter = function( dat, time=1:length(dat), chunkWidth = NA, numChunks = NA){

	if (!is.na(chunkWidth) && !is.na(numChunks))
		stop("Error: cannot specify both chunkWidth and numChunks at this time")
	if (is.na(chunkWidth) && is.na(numChunks))
		numChunks = 10
	if (is.na(numChunks))
		numChunks = round( diff(range(time)) / chunkWidth)
	if (is.na(chunkWidth))
		intervalWidth = diff(range(time))/numChunks


# # 	if ( sum(sort(time) != time) > 0 )
		# stop("Bad time values")		#Could happen if time is NA
									# #Overly cautious anyway, values could be
									# # scrambled and algorithm would still work

	partitionPoints = seq(min(time), max(time), len = numChunks+1)	
	partitionPoints[1] = -Inf
	partitionPoints[numChunks+1] = Inf
	#print(partitionPoints)		#TODO
	
	datChunks = list()
	timeChunks = list()
	#t<<-time				#TODO
	#print(time)
	for (i in 1 : numChunks){
		low = partitionPoints[i]
		high = partitionPoints[i+1]
		chunkInds = which( (low <= time) & (time < high) )
		#print(chunkInds) #TODO
		#print(c(low, high))
		datChunks[[i]] = dat[chunkInds]
		timeChunks[[i]] = time[chunkInds]
	}
	return( list(datChunks, timeChunks) )
}


#TODO tweak significance level
#Returns 1 if a spike, -1 if a dip, 0 otherwise
defSignif=0.001
spikeDetZ = function( chunk, time=1:length(chunk), signif = defSignif){
	zAlpha = abs(qnorm(signif))
	scaledDat = scale(chunk)
	spikes = sum(scaledDat > zAlpha)
	dips = sum(scaledDat < -zAlpha)
	
		#plot(1:length(chunk),chunk); print(spikes); print(dips)		#Sanity Check
		
	return(sign(spikes - dips))
}

#Returns 1 if increasing trend, -1 if decreasing trend, 0 otherwise
defTrendSig=0.0001
trendDetZ = function(chunk, time=1:length(chunk), signif = defTrendSig){
	zAlpha = abs(qnorm(signif))
	r = cor(chunk, time)
	w = atanh(r) 					#w = 0.5*log((1+r)/(1-r))
	se = 1/sqrt(length(chunk)-3)
	
	if (abs(w/se) > zAlpha){
		return(sign(w))
	} else {
		return(0)
	}
}


spikeDetEmp = function( chunk, time=1:length(chunk), bound=5){
	med = median(chunk)
	spikeHere = sum( chunk > med*bound)
	dipHere = sum( chunk < med/bound)
	if (spikeHere > 0 || dipHere > 0){
	cat("Spikes:", spikeHere)
	cat("\nDips:", dipHere)
	cat("\n")
	}
	return(sign(spikeHere - dipHere))
}

trendDetEmp = function(chunk, time=1:length(chunk), bound = 8/4){
	#med = median(chunk)
	fit = lm(chunk~time)
	endPoints = fit[[1]][2]*c(time[1],time[length(time)]) + fit[[1]][1]
	growth = endPoints[2]/endPoints[1]
	if (growth > bound)
		return(1)
	growth = 1/growth
	if (growth > bound)
		return(-1)
	return(0)
}

#Classifies a chunk as 
classifier = function( chunk, time=1:length(chunk), mode="new" ){
	if (length(chunk) < 5){
		return(NULL_)
	}
	
	spikeDet = trendDet = NA
	if (mode == "old"){
		spikeDet = spikeDetZ
		trendDet = trendDetZ
	}else{
		spikeDet = spikeDetEmp
		trendDet = trendDetEmp	
	}

	if (length(chunk) < 5 || NA %in% chunk){
		return(NULL_)
	}
	spikePresence = spikeDet(chunk,time)
	if (spikePresence){
		return(ifelse(spikePresence == 1, SPIKE, DIP))
	}
	
	trendPresence = trendDet(chunk,time)
	if(trendPresence){
		return(ifelse(trendPresence == 1, INC, DEC))
	}
	
	return(FLAT)
}














