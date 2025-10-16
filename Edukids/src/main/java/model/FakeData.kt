package model

object FakeData {

    fun getFakeExercises(): List<Exercise> {
        val media1 = Media(1, "O que é isso?", AgeRange.INFANT, MediaType.IMAGE, "https://img.freepik.com/fotos-gratis/kitty-com-parede-monocromatica-atras-dela_23-2148955134.jpg")

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

        val media3 = Media(3, "Galinha pintadinha", AgeRange.INFANT, MediaType.VIDEO, "1i7p0vTGcBk")

        val options3 = mutableListOf(
            Option(1, "Pavão"),
            Option(2, "Peru"),
            Option(3, "Urubu")
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
                description = "O doutor é qual animal?",
                correctOptionId = 1,
                isReady = true,
                points = 10,
                medias = mutableListOf(media3),
                options = options3
            )
        )
    }

    fun getFakeActivities(): List<Activity> {
        val mediaActivity = Media(10, "Pular corda", AgeRange.INFANT, MediaType.IMAGE, "https://img.freepik.com/fotos-gratis/uma-garota-sorridente-pulando-corda-vermelha_23-2149073589.jpg")

        return listOf(
            Activity(
                _id = 100,
                description = "Pule a corda 15 vezes!",
                isCompleted = false,
                isReady = true,
                media = mediaActivity
            )
        )
    }
}