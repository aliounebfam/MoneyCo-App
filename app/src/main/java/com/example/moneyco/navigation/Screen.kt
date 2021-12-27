package com.example.moneyco.navigation


const val ROOT_ROUTE = "root"
const val AUTH_ROUTE = "auth"
const val MAIN_ROUTE = "home"
const val REVENU_ROUTE = "revenu"

const val CATEGORIE = "categories"
const val SOUS_CATEGORIE = "sous_categories"


sealed class Screen(val route: String) {
    object LogIn : Screen(route = "logIn_screen")
    object SignUp : Screen(route = "signUp_screen")
    object MainHome : Screen(route = "main_screen")
    object CategorieRevenu : Screen(route = "categorie_revenu_screen")
    object SousCategorieRevenu : Screen(route = "sous_categorie_revenu_screen/{$CATEGORIE}") {
        fun passCategorie(
            categorie: String
        ): String {
            return "sous_categorie_revenu_screen/$categorie"
        }
    }

    object AjouterRevenu : Screen(route = "ajouter_revenu_screen/{$CATEGORIE}/{$SOUS_CATEGORIE}") {
        fun passArguments(
            categorie: String,
            sous_categorie: String = " "
        ): String {
            return "ajouter_revenu_screen/$categorie/$sous_categorie"
        }
    }
}
