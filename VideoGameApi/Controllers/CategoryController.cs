using Data;
using Microsoft.AspNetCore.Mvc;
using Models;

namespace VideoGameApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class CategoryController : ControllerBase
    {
        private readonly DataContext _context;

        public CategoryController(DataContext context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult GetCategories()
        {
            return Ok(_context.Categories);
        }

        [HttpPost]
        public IActionResult CreateCategory(Category category)
        {
            if (category == null)
            {
                return BadRequest("Invalid input!");
            }

            _context.Add(category);
            _context.SaveChanges();

            return Ok(category);
        }

        [HttpPut]
        public IActionResult UpdateCategory(Category category)
        {
            if (category == null)
            {
                return BadRequest("Invalid input!");
            }

            Category categoryRef = _context.Categories.FirstOrDefault(x => x.Id == category.Id);
            if (categoryRef == null)
            {
                return BadRequest("Element doesn't exist!");
            }

            _context.Update(categoryRef);
            _context.SaveChanges();

            return Ok(categoryRef);
        }

        [HttpDelete]
        public IActionResult DeleteCategory(string? name)
        {
            if (name == null)
            {
                return BadRequest("Invalid input!");
            }

            Category categoryRef = _context.Categories.FirstOrDefault(x => x.Name == name);
            if (categoryRef == null)
            {
                return BadRequest("Element doesn't exist!");
            }

            _context.Remove(categoryRef);
            _context.SaveChanges();

            return Ok(categoryRef);
        }
    }
}
