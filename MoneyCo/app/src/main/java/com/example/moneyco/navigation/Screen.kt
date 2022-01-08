package com.example.moneyco.navigation


const val ROOT_ROUTE = "root"
const val AUTH_ROUTE = "auth"
const val MAIN_ROUTE = "home"
const val REVENU_ROUTE = "revenu"
const val DEPENSE_ROUTE = "depense"

const val CATEGORIE = "categories"
const val SOUS_CATEGORIE = "sous_categories"
const val ID_DOC = "id_doc"

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

    object CategorieDepense : Screen(route = "categorie_depense_screen")
    object SousCategorieDepense : Screen(route = "sous_categorie_depense_screen/{$CATEGORIE}") {
        fun passCategorie(
            categorie: String
        ): String {
            return "sous_categorie_depense_screen/$categorie"
        }
    }

    object AjouterDepense :
        Screen(route = "ajouter_depense_screen/{$CATEGORIE}/{$SOUS_CATEGORIE}") {
        fun passArguments(
            categorie: String,
            sous_categorie: String = " "
        ): String {
            return "ajouter_depense_screen/$categorie/$sous_categorie"
        }
    }

    object EditerTransaction :
        Screen(route = "edit_transaction/{$ID_DOC}") {
        fun passId(
            idDoc: String
        ): String {
            return "edit_transaction/$idDoc"
        }
    }
}
