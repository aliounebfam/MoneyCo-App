# MoneyCo

### MoneyCo est une application de gestion de budget personnel, développée avec Android Studio (Kotlin) en utilisant le framework [Jetpack Compose](https://developer.android.com/jetpack/compose).

## Nouvelle architecture (SQLite)

L'application a été migrée depuis Firebase vers une architecture locale utilisant SQLite avec Room :

### Couches d'architecture

- **UI Layer**: Jetpack Compose pour l'interface utilisateur
- **ViewModel Layer**: Gestion de l'état et logique de présentation
- **Repository Layer**: Abstraction de la source de données
- **Data Layer**: Room Database pour le stockage local

### Technologies utilisées

- **Room**: Pour la persistance des données locales
- **Hilt**: Pour l'injection de dépendances
- **Kotlin Coroutines & Flow**: Pour les opérations asynchrones et réactives
- **Jetpack Compose**: Pour l'interface utilisateur déclarative
- **Navigation Compose**: Pour la navigation entre les écrans
- **EncryptedSharedPreferences**: Pour stocker les informations d'authentification de manière sécurisée

## Structure de la base de données

La base de données locale se compose des tables suivantes :

- **users**: Stocke les informations de profil utilisateur
- **transactions**: Enregistre toutes les transactions (revenus et dépenses)
- **categories**: Stocke les catégories de transactions
- **subcategories**: Stocke les sous-catégories liées aux catégories principales

## Fonctionnalités

- Authentification locale (plus de dépendance à Firebase)
- Gestion des transactions (ajout, modification, suppression)
- Catégorisation des transactions
- Filtrage et recherche des transactions
- Visualisation des dépenses et revenus
- Budget personnalisé

## Quelques captures

<img src="https://raw.githubusercontent.com/UnityABF/MoneyCo/main/Captures%20d'%C3%A9cran/splashScreen.png" alt="splash screen" width="300" height="625"> <img src="https://raw.githubusercontent.com/UnityABF/MoneyCo/main/Captures%20d'%C3%A9cran/LoginScreen.png" alt="splash screen" width="300" height="625">

<img src="https://github.com/UnityABF/MoneyCo/blob/main/Captures%20d'%C3%A9cran/HomeScreen4.png" alt="splash screen" width="300" height="625">  <img src="https://github.com/UnityABF/MoneyCo/blob/main/Captures%20d'%C3%A9cran/TransactionScreen2.png" alt="splash screen" width="300" height="625">

### N'hesitez pas à jeter un coup d'oeil aux autres différentes captures d'écran de l'application😉.

## Installation

1. Cloner le dépôt
2. Ouvrir le projet dans Android Studio
3. Synchroniser le projet avec les fichiers Gradle
4. Exécuter l'application sur un émulateur ou un appareil physique

## Améliorations architecturales

1. **Clean Architecture** : Séparation claire des responsabilités avec les couches données, domaine et présentation
2. **Repository Pattern** : Abstraction de la source de données pour faciliter les tests et la maintenance
3. **Composants réutilisables UI** : Création de composants UI réutilisables pour une meilleure cohérence et maintenance
4. **Tests** : Ajout de tests unitaires et d'instrumentation pour les composants clés