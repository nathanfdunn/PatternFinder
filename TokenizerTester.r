

source("Tokenizer.r")

source("FakeVals.R")
#source("tokePlotter2.r")
source("tokePlotter.r")

#testTokenPlot = function(vals, times = 1:length(vals), width=100){

#}


#chunks = splitter(mynewvals)

#print(chunks)

#tokes = tokenize(mynewvals)

#print(tokes)


csvtoplot = function(fileName, timeInd, quantIDInd, flipTimes = T, numTokens=100){
	fileName = paste("DataSets_R/",fileName,sep="")
	mytable <<- read.csv(fileName)
	t <<- mytable[[timeInd]]* (1+(-2)*flipTimes)
	x <<- mytable[[quantIDInd]]
	myPlot2(x, t, numChunks = numTokens)
}

# csvtoplot("nfdunn_LawDome.csv",13,9, numTokens = 20)
# readline()
 csvtoplot("nfdunn_GISP2.csv",14, 6, F, numTokens = 20)
# readline()
#csvtoplot("nfdunn_Belukha_Ions_Annual.csv", 8, 2, F, 10)
#csvtoplot("nfdunn_Moulton1.csv", 10, 8, numTokens = 15)
