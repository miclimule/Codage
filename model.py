# get data
import pandas

prediction = pandas.read_csv("prediction.csv")

# print(prediction.columns)
# print(prediction.shape)
# print(prediction)
print("--------------------------------------------------------")

# Pour avoir un graph des donnees
import matplotlib.pyplot as plt

# les autres types de graphe se trouve dans la doc
# plt.hist(prediction["size"])

# plt.show()

# prediction[prediction["size"] > 4] permet d'afficher les donnees suivantes une condition
# .iloc[0] permet d'afficher que la ligne choisie
# print(prediction[(prediction["size"] > 4) & (prediction["min"] > 2)].iloc[0])
print("--------------------------------------------------------")

# Suppression de ligne
# prediction = prediction.drop(prediction[prediction["code"] == True].index)

# Supprime les lignes contenant des valeurs manquantes NaN. 1 = colonne et 0 = ligne
# prediction = prediction.dropna(axis=1)

# Remplace tout les true en 0 et les false en 1
prediction["code"] = prediction["code"].replace({True: 0, False: 1})
# print(prediction)

print("--------------------------------------------------------")

# Clusturing cree le modele
from sklearn.cluster import KMeans
kmeans_model = KMeans(n_clusters=5, random_state=0, n_init=20)
good_columns = prediction._get_numeric_data()
kmeans_model.fit(good_columns)
# Obtenir les labels des clusters.
labels = kmeans_model.labels_

# Tracage 
from sklearn.decomposition import PCA
pca_2 = PCA(2)
# adapter le modèle PCA aux colonnes numériques précédentes.
plot_columns = pca_2.fit_transform(good_columns)
# Faire un graphique à nuage de points a partir du cluster
plt.scatter(x=plot_columns[:,0], y=plot_columns[:,1], c=labels)
# Afficher le graphique.
# plt.show()

print("--------------------------------------------------------")

# Mise en place de la prediction
# recherche de corelations
print(prediction.corr()["code"])

# Obtenir toutes les colonnes du DataFrame.
columns = prediction.columns.tolist()
# Filtrer les colonnes.
columns = [c for c in columns if c not in ["longeurfixe", "prefixe","unique", "factorisable","min", "max","size"]]
# Stocker la variable a prédire.
target = "code"

print("--------------------------------------------------------")

# creation des datas de test et de train
from sklearn.model_selection import train_test_split
# frac est le pourcentage a prendre dans train
train = prediction.sample(frac=0.8, random_state=0)
# Sélectionner tout ce qui n'est pas dans le set de training et le mettre dans le set de test.
test = prediction.loc[~prediction.index.isin(train.index)]
# Afficher les dimensions des 2 sets.
print(train.shape)
print(test.shape)

print("--------------------------------------------------------")
# Importer le modèle LinearRegression.
from sklearn.linear_model import LinearRegression
from sklearn.metrics import r2_score
# Initialiser la classe du modèle.
model = LinearRegression()
# Adapter le modèle aux données d'entrainement
model.fit(train[columns], train[target])
# Importer la fonction de calcul d'erreur depuis scikit-learn.
from sklearn.metrics import mean_squared_error
# Générer des prédictions pour le set de test.
predictions = model.predict(test[columns])
# Calculer l'erreur entre nos prédictions et les valeurs réelles que nous connaissons.
print(mean_squared_error(predictions, test[target]))
print(r2_score(test[target], predictions))

print("--------------------------------------------------------")
# Importer le modèle random forest.
from sklearn.ensemble import RandomForestRegressor
# Initialiser le modèle avec certains paramètres.
modela = RandomForestRegressor(n_estimators=100, min_samples_leaf=10, random_state=0)
# Adapter le modèle aux données.
modela.fit(train[columns], train[target])
# Faire des prédictions.
predictions = modela.predict(test[columns])
# Calculer l'erreur.
print(mean_squared_error(predictions, test[target]))
print(r2_score(test[target], predictions))