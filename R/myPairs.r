myPairs = function(frame, listOfVals){
	cols = c(rep("black", dim(frame)[1]), rep("blue", length(listOfVals)))
	for (val in listOfVals)
		frame[dim(frame)[1]+1,] = val
	pairs(frame, col=cols)  
}