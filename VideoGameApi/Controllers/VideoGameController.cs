using Data;
using Microsoft.AspNetCore.Mvc;
using Models;

namespace VideoGameApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class VideoGameController : ControllerBase
    {
        private readonly DataContext _context;

        public VideoGameController(DataContext context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult GetVideoGames() 
        {
            return Ok(_context.Games); 
        }

        [HttpPost]
        public IActionResult CreateVideoGame(VideoGame videoGame) 
        {
            if (videoGame == null)
            {
                return BadRequest("Invalid input!");
            }

            _context.Add(videoGame);
            _context.SaveChanges();

            return Ok(videoGame); 
        }

        [HttpPut]
        public IActionResult UpdateVideoGame(VideoGame videoGame) 
        {
            if (videoGame == null)
            {
                return BadRequest("Invalid input!");
            }

            VideoGame gameRef = _context.Games.FirstOrDefault(x => x.Id == videoGame.Id);
            if (gameRef == null)
            {
                return BadRequest("Element doesn't exist!");
            }

            _context.Update(gameRef);
            _context.SaveChanges();

            return Ok(gameRef); 
        }

        [HttpDelete]
        public IActionResult DeleteVideoGame(string? name) 
        {
            if (name == null)
            {
                return BadRequest("Invalid input!");
            }

            VideoGame gameRef = _context.Games.FirstOrDefault(x => x.Name == name);
            if (gameRef == null)
            {
                return BadRequest("Element doesn't exist!");
            }

            _context.Remove(gameRef);
            _context.SaveChanges();

            return Ok(gameRef); 
        }
    }
}
