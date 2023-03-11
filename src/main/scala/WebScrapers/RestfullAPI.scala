package WebScrapers

import cats._
import cats.effect._
import cats.implicits._
import cats.syntax.all._
import io.circe._
import io.circe.generic.auto._
import io.circe.generic.semiauto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.dsl.impl._
import org.http4s.headers._
import org.http4s.implicits._
import org.http4s.server._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.collection.mutable

object RestfullAPI extends IOApp {

  case class Movie(id: Int, title: String, release_year: Int, director: String)

  implicitly[Encoder[Movie]]
  implicitly[Decoder[Movie]]

  val moviesDB: mutable.Map[Int, Movie] = mutable.Map(
    1 -> Movie(1, "Star Wars : Episode IV - A New Hope", 1977, "George Lucas"),
    2 -> Movie(2, "Star Wars : Episode V - The Empire Strikes Back", 1980, "Irvin Kershner"),
    3 -> Movie(3, "Star Wars : Episode VI - Return of the Jedi", 1983, "Richard Marquand"),
    4 -> Movie(4, "Star Wars : Episode I - The Phantom Menace", 1999, "George Lucas"),
    5 -> Movie(5, "Star Wars : Episode II - Attack of the Clones", 2002, "George Lucas"),
    6 -> Movie(6, "Star Wars : Episode III - Revenge of the Sith", 2005, "George Lucas"),
    7 -> Movie(7, "Star Wars : The Force Awakens", 2015, "J.J. Abrams"),
    8 -> Movie(8, "Rogue One : A Star Wars Story", 2016, "Gareth Edwards"),
    9 -> Movie(9, "Star Wars : The Last Jedi", 2017, "Rian Johnson"),
    10 -> Movie(10, "Solo : A Star Wars Story", 2018, "Ron Howard"),
    11 -> Movie(11, "Star Wars : The Rise of Skywalker", 2019, "J.J. Abrams")
  )


  object IdQueryParamMatcher extends QueryParamDecoderMatcher[Int]("id")

  private def movieRoutes[F[_] : Concurrent]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl._
    implicit val movieDecoder: EntityDecoder[F, Movie] = jsonOf[F, Movie]

    HttpRoutes.of[F] {
      case GET -> Root / "movies" =>
        Ok(moviesDB.asJson)

      case GET -> Root / "movies" / id =>
        moviesDB.get(id.toInt) match {
          case Some(movie) => Ok(movie.asJson)
          case _ => NotFound("Not found")
        }

      case DELETE -> Root / "movies" / id =>
        moviesDB.get(id.toInt) match {
          case Some(movie) => moviesDB -= movie.id
            Ok()
          case _ => NotFound("Not found")
        }

//      case req@POST -> Root / "movies" =>
//        val content = req.body
//        val jsonObject = content.asJson
//
//        moviesDB.put(
//          moviesDB.size + 1,
//          Movie(
//            moviesDB.size + 1,
//            jsonObject.asInstanceOf[Map[String, Any]].apply("title").toString,
//            jsonObject.asInstanceOf[Map[String, Any]].apply("title").toString,
//            jsonObject.asInstanceOf[Map[String, Any]].apply("title").toString,
//          )
//        )
//        Ok()

      //      case req@PUT -> Root / "movies" /:? IdQueryParamMatcher("id") => ???
      //      case req@PATCH -> Root / "movies" /:? IdQueryParamMatcher("id") => ???
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val apis = Router(
      "/api" -> movieRoutes[IO],
    ).orNotFound

    BlazeServerBuilder[IO](runtime.compute)
      .bindHttp(8080, "localhost")
      .withHttpApp(apis)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
