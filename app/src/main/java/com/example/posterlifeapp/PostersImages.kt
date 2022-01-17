package com.example.posterlifeapp

data class PostersImages(
    val poster: Int,
    val author: String,
    val title: String
)



fun PosterList(): List<PostersImages>{
    return listOf<PostersImages>(
        PostersImages( R.drawable.hyldest_til_hverdagen_danturell, "Dan Turell", "Hyldest til hverdagen"),
        PostersImages( R.drawable.min_mund_og_mit_hjerte_1, "N.F.S. Grundtvig", "Min mund og mit hjerte"),
        PostersImages( R.drawable.sommerfugledalen_1991_inger_christensen, "Inger Christensen", "Sommerfugledalen"),
        PostersImages( R.drawable.angst_tom_kristensen, "Tom Kristensen", "Angst"),
        PostersImages( R.drawable.billiesblues_dt, "Dan Turèll", "Billie's Blues"),
        PostersImages( R.drawable.introduction_1917_emil_boennelycke, "Emil Bønnelycke", "Introduction"),
        PostersImages( R.drawable.brombaer_bodilbech, "Bodil Bech", "Brombær"),
        PostersImages( R.drawable.mitdigt_michaelstrunge, "Michael Strunge", "Mit digt 1985"),
        PostersImages( R.drawable.du_som_vaged_over_barnelivet, "N.F.S. Grundtvig", "Du som våged over barnelivet"),
        PostersImages( R.drawable.jeg_civiliserer_mig_om_morgenen_1992_kirsten_hammann, "Kirsten Hammann", "Jeg civiliserer mig om morgenen"),
    )

}



