# Bon Appetit

### Inspiration
When you walk into a new Korean restaurant, and look at the menu, there are so many dishes that we don't know of. **Bon Appetit creates a solution to give a better interpretation of the menu, by presenting recommendations and pictures of the dishes.**

### What it does
The user takes a picture of the menu, and there will be automatic text blocks that identify the dish names, if you tap on one of them, it will pop out a brief summarization of the menu as well as a picture of it. The source is almost unlimited, because we extract the information from Bing and wikipedia. It will also collect the user's history, and have a nice recommendation of the most-fitted food in this menu. The recommendation window ranks all the dishes found in the menu.

### How we built it
We built the front end via Android Studio with SQLite database, and the back end using Jupyter notebook, intensively using sklearn and numpy. We used Google OCR API in the vision part and block identification, and we made many modifications to fit our design. The significant part of the project is the building of database. The data based we used are gathered via internet. We used a google Knowledge graph API, and used Bing and wiki-cook book as the primary source of information. In the Machine learning back end, we first gather a sparse array of each dishes, and run PCA on the data, adjusing the hyperparameters and tried the EM learning algorithm, with a feedback updating loop to update the user's preference.

### Accomplishment that we are proud of

- The database comes from information extraction online, rather than a ready dataset.
- The nice merge of java and python.
- The machine learning techniques that we used. We got the inspiration from NLP word embedding, and compute the distance between vectors in the euclidean space.

### What's next 
We will further train the model, and adjust more hyperparameters. We will make the data extraction process more robust.
