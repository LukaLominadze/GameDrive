using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Models;

namespace Data
{
    public class DataContext
    {
        const string FILE_PATH = "../api/";

        public List<VideoGame> Games = new List<VideoGame>();
        public List<Category> Categories = new List<Category>();

        public DataContext()
        {
            Load();
        }

        public void Add(VideoGame videoGame) 
        {
            if (Games.Count > 0)
            {
                videoGame.Id = Games.Last().Id + 1;
            }
            Games.Add(videoGame);
        }
        public void Add(Category category) 
        {
            if (Categories.Count > 0)
            {
                category.Id = Categories.Last().Id + 1;
            }
            Categories.Add(category);
        }

        public void Update(VideoGame videoGame) 
        {
            VideoGame gameRef = Games.FirstOrDefault(x => x.Id == videoGame.Id);
            gameRef.Name = videoGame.Name;
            gameRef.Description = videoGame.Description;
            gameRef.Categories = videoGame.Categories;
        }

        public void Update(Category category) 
        {
            Category categoryRef = Categories.FirstOrDefault(x => x.Id == category.Id);
            categoryRef.Name = category.Name;
        }

        public void Remove(VideoGame videoGame) 
        {
            Games.Remove(videoGame);
        }

        public void Remove(Category category) 
        {
            Categories.Remove(category);
        }

        public void SaveChanges() 
        {
            {
                string json = JsonSerializer.Serialize(Games, new JsonSerializerOptions { WriteIndented = true });
                File.WriteAllText($"{FILE_PATH}games.json", json);
            }
            {
                string json = JsonSerializer.Serialize(Categories, new JsonSerializerOptions { WriteIndented = true });
                File.WriteAllText($"{FILE_PATH}categories.json", json);
            }
        }

        private void Load() 
        {
            if (File.Exists($"{FILE_PATH}games.json"))
            {
                string json = File.ReadAllText($"{FILE_PATH}games.json");
                Games = JsonSerializer.Deserialize<List<VideoGame>>(json);
            }
            if (File.Exists($"{FILE_PATH}categories.json"))
            {
                string json = File.ReadAllText($"{FILE_PATH}categories.json");
                Categories = JsonSerializer.Deserialize<List<Category>>(json);
            }
        }
    }
}
