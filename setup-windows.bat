@echo off
REM copy program.cs, because it will be overriden
xcopy "VideoGameApi\Program.cs" "vendor\bin\scripts\ProjectFiles\" /Y

REM create solution
dotnet new sln -n VideoGameApi --force

REM create api project and readd main file
dotnet new webapi -n VideoGameApi --force
xcopy "vendor\bin\scripts\ProjectFiles\Program.cs" "VideoGameApi\Program.cs" /Y

REM create models project
dotnet new classlib -n Models --force
erase "Models\Class1.cs"

REM create data project
dotnet new classlib -n Data --force
erase "Data\Class1.cs"

REM add projects to solution
dotnet sln VideoGameApi.sln add VideoGameApi/VideoGameApi.csproj
dotnet sln VideoGameApi.sln add Data/Data.csproj
dotnet sln VideoGameApi.sln add Models/Models.csproj

REM add packages to projects
dotnet add VideoGameApi/VideoGameApi.csproj package Swashbuckle.AspNetCore.Swagger
dotnet add VideoGameApi/VideoGameApi.csproj package Swashbuckle.AspNetCore.SwaggerGen
dotnet add VideoGameApi/VideoGameApi.csproj package Swashbuckle.AspNetCore.SwaggerUI
dotnet add VideoGameApi/VideoGameApi.csproj package AutoMapper
dotnet add Models/Models.csproj package AutoMapper

REM add references to api project
dotnet add VideoGameApi/VideoGameApi.csproj reference Data/Data.csproj
dotnet add VideoGameApi/VideoGameApi.csproj reference Models/Models.csproj

REM add references to data project
dotnet add Data/Data.csproj reference Models/Models.csproj
pause