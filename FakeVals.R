mynewvals = rep(0,600)


t=seq(0,2*pi,len=100)

flat = rnorm(100) + sin(t + 3*rnorm(100)) + 2*cos(8*t + 7*rnorm(100))

spike = rnorm(100) + sin(t + 3*rnorm(100)) + 2*cos(8*t + 7*rnorm(100))
spike[35:45] = spike[35:45] +5+ 3*rnorm(11)^2


dec = 3*scale(-(1:100)) + 2*rnorm(100) 

inc = 3*scale((1:100)) + 2*rnorm(100) 

dip = rnorm(100) + sin(t + 3*rnorm(100)) + 2*cos(8*t + 7*rnorm(100)) + scale(1:100)
dip[35:45] = dip[35:45] -3- 3*rnorm(11)^2



mynewvals[1:100] = flat 
mynewvals[101:200] = spike
mynewvals[201:300] = dec - 5
mynewvals[301:400] = inc - 5
mynewvals[401:500] = dip
mynewvals[501:600] = dec - 5 + runif(100)

mynewvals = mynewvals - min(mynewvals) + 0.05

mynewvals = mynewvals / 100



oscTime = 2*pi*seq(0,5, len=1000)
oscDat = cos(oscTime) + rnorm(1000)/2