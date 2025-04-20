@echo off
xcopy "VideoGameApi\Program.cs" "vendor\bin\scripts\ProjectFiles" /Y

dotnet new sln -n VideoGameApi --force

dotnet new webapi -n VideoGameApi --force
xcopy "vendor\bin\scripts\ProjectFiles\Program.cs" "VideoGameApi\Program.cs" /Y

dotnet sln VideoGameApi.sln add VideoGameApi/VideoGameApi.csproj
pause