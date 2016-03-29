# ZAKO2  
Go to repository Dockerfile [https://github.com/Manfed/Dockerfile.git](https://github.com/Manfed/Dockerfile.git)  
[Che]  
Create new workspace. In 'Select stack' section choose CUSTOM STACK->Write your own stack and paste there Dockerfile content  
Set a name for workspace and select 'Create Workspace'.  
In new workspace view click arrow sign in up-right corner.  
When workspace starts select: Workspace->Import Project->Git and paste [https://github.com/Manfed/ZAKO2](https://github.com/Manfed/ZAKO2) into URL field.  
Choose project name, and don't forget it. Import project.    
You can create commands to build and run a project(middle of the screen, under menu bar - CMD - Edit Commands - Custom) e.g.:    
[Build]  
cd /projects/ZAKO && ant compile jar    
[Run]  
cd /projects/ZAKO && ant compile jar run    
To check IP under project is available choose machine view (up-right corner)->Servers. Project is available under port 10000.  
