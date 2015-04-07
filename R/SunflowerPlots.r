
blah = function(){
read.table("~/Programming/PatternFinder/PatternDetection/AccTable_g_k_mw4",T)->acc

acc$Max[ which(acc$Max == Inf) ] = 10000

acc$Max = log(acc$Max, 10)

Gamma = acc$Gamma
Accuracy = acc$Acc
K = acc$K

sunflowerplot(Gamma, Accuracy)
readline("Pause")
sunflowerplot(K, Accuracy)
readline("Pause")
sunflowerplot(acc$Max, Accuracy, xlab="log( Max Weight )")

inds = which(acc$Acc == max(acc$Acc))
print(max(acc$Acc))
print(acc[inds,])


}


