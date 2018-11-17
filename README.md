Synthetic Graph Generator :

In this project I implemented a synthetic graph generator in this project which will create different kinds of graph network based on given vertex, edge and cohesion. Visualization is also developed based on the generated synthetic graph.

I target for the star and clique in the project.
The input of the project is :

No of Star :
No of Vertices in the Star :
No of Clique : 
No of Vertices in the Clique :
Cohesion Value :

Based on the cohesion value the stars and cliques will be strongly connected between each other. Cohesion value range is : -1<cohesion value <1. Floating value is accepted for the 
cohesion.


I calculated the Newman Girvan Modularity(Q) here.
    Equation is : Q = (summation of all the modules)[(ls/L) - square(ds/2L)]
    here ls = number of links between nodes in the module s
         L =  number of links in the network
         ds = sum of the degrees of the nodes in module s

To execute the project you need an editor that supports java. I completed the project in Netbeans editor. If you execute the project then two kinds of file will be exported.
1. finaltest.dot
2. d3viz.json

dot file is used for the next project which we will include with this project later. And the jSON file is used for the better visualization. There is a index.html.
Which is reading the data from the d3viz.json file and showing the output based on that. 

You will find everything in the github with the much update as I have plan to modify it more with some ideas of implementing several kinds of synthetic generator.

https://github.com/thedhrubo/modularity
