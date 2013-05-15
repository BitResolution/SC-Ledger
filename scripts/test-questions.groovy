load "data"

println "What was the total value of all common stock positions in the fund for each quarter?"
println "Did the fund grow or fall in value with respect to its common stock positions over the 4 quarters?"
println summariseStock(name: "COM")

println "What would have been the 5 largest holdings of common stock that were publically available "
println "on 12 August 2012 for the fund manager?"
println findLargestPositions(count: 5, date: "20120812")

println "As at 12/31/2012, what were the fund’s 3 biggest new common stock "
println "positions (stocks it had not held in the previous quarter)?"
println findTopNewPerformers(count: 3, date: "20121231")


