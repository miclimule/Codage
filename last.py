import joblib
import sys

arg1 = sys.argv[1]

# Chargement du modèle
model = joblib.load('modele_decision_tree.joblib')

# Préparation des données
donnees = [eval(arg1)]

# Prédiction
prediction = model.predict(donnees)

# Affichage du résultat
print(prediction)
