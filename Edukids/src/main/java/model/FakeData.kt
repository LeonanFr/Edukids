package model

object FakeData {

    fun getFakeExercises(): List<Exercise> {
        val media1 = Media(1, "O que é isso?", AgeRange.INFANT, MediaType.IMAGE, "https://img.freepik.com/fotos-gratis/um-gatinho-fofo-de-itado-na-cama_1340-38827.jpg")

        val options1 = mutableListOf(
            Option(1, "Um gato"),
            Option(2, "Um cachorro"),
            Option(3, "Um pássaro")
        )

        val media2 = Media(2, "Qual a cor do céu?", AgeRange.INFANT, MediaType.TEXT, "")

        val options2 = mutableListOf(
            Option(1, "Verde"),
            Option(2, "Azul"),
            Option(3, "Vermelho")
        )

        val media3 = Media(3, "Mundo Bita - Viajar pelo Safari", AgeRange.INFANT, MediaType.VIDEO, "f53hQtDA3_s")

        val options3 = mutableListOf(
            Option(1, "Fazendinha"),
            Option(2, "Fundo do Mar"),
            Option(3, "Safari")
        )


        return listOf(
            Exercise(
                _id = 1,
                description = "Que animal é este?",
                correctOptionId = 0,
                isReady = true,
                points = 10,
                medias = mutableListOf(media1),
                options = options1
            ),
            Exercise(
                _id = 2,
                description = "Responda a pergunta:",
                correctOptionId = 1,
                isReady = true,
                points = 10,
                medias = mutableListOf(media2),
                options = options2
            ),
            Exercise(
                _id = 3,
                description = "Onde os animais do vídeo estão passeando?",
                correctOptionId = 2,
                isReady = true,
                points = 10,
                medias = mutableListOf(media3),
                options = options3
            )
        )
    }

    fun getFakeActivities(): List<Activity> {
        val mediaActivity = Media(10, "Encontre a senha", AgeRange.INFANT, MediaType.IMAGE, "https://i.imgur.com/83n2g6b.png")

        return listOf(
            Activity(
                _id = 100,
                description = "A senha é 1234. Digite para completar a atividade!",
                isCompleted = false,
                isReady = true,
                media = mediaActivity
            )
        )
    }
}