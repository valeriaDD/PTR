package WebScrapers

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}



object QuotesScraper extends App {
  implicit val system: ActorSystem = ActorSystem("system")

  private val request = HttpRequest(uri = "https://quotes.toscrape.com/")
  private val responseFuture = Http().singleRequest(request)

//  printResponseInfo(responseFuture)
  parseQuotes(responseFuture)

  private def printResponseInfo(responseFuture: Future[HttpResponse]): Unit = {
    responseFuture.map { response =>
      println(s"HTTP status code: ${response.status}")
      println("HTTP response headers:")
      response.headers.foreach(h => println(s"${h.name}: ${h.value}"))
      println("HTTP response body:")
      response.entity.dataBytes.runForeach { chunk =>
        println(chunk.utf8String)
      }
    }
  }

  private def parseQuotes(responseFuture: Future[HttpResponse]): Unit = {
    responseFuture.flatMap { response =>
      response.entity.dataBytes
        .runFold("")(_ + _.utf8String) //consume the response body bytes as a stream, and concatenate them into a single string
        .map { responseBody =>
          val quoteRegex = """<span class="text" itemprop="text">([^<]+)</span>""".r
          val authorRegex = """<span>by <small class="author" itemprop="author">([^<]+)</small>""".r
          val tagRegex = """<meta class="keywords" itemprop="keywords" content="([^<]+)" /    >""".r

          quoteRegex.findAllMatchIn(responseBody).map { quoteMatch =>
            val quoteText = quoteMatch.group(1).toString
            val authorName = authorRegex.findFirstMatchIn(quoteMatch.after.toString).get.group(1).toString
            val tags = tagRegex.findFirstMatchIn(quoteMatch.after.toString).get.group(1).split(",").toList

            Map("author" -> authorName, "quote" -> quoteText, "tags" -> tags)
          }.toList
        }
    }.onComplete{
        case Success(response) =>
          response.foreach(println)

        case Failure(e) =>
          println("Request failed: " + e.getMessage)
    }
  }
}