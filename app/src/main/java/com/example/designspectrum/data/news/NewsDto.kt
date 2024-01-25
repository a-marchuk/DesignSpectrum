package com.example.designspectrum.data.news

data class NewsDto (
    val imageURL : String,
    val title : String,
    val newsURL : String
)
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
) {
    fun toNewsDto(): NewsDto {
        return NewsDto(
            imageURL = this.urlToImage ?: "",
            title = this.title,
            newsURL = this.url
        )
    }
}


data class Source(
    val id: String?,
    val name: String
)
