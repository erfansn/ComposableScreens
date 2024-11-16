## How to Contribute to Composable Screens

1. **Match Your Design to [Existing Categories](https://github.com/erfansn/ComposableScreens/tree/master/category)**  
   - Ensure the design fits within one of the existing categories in the project and includes at least two pages.  
   - If no suitable category is found, request a new category in the **Discussions** [section](https://github.com/erfansn/ComposableScreens/discussions/1).

2. **Create a New Issue**  
   - Use the **Add New Design** [issue template](https://github.com/erfansn/ComposableScreens/issues/new/choose) to create an issue for the design you want to implement.  

3. **Fork the Repository**  
   - Fork the project and create a new branch with a name of your choice for applying your changes.

4. **Create a Module and write your code**  
   - Use the following task to generate a module under your desired category and name in **kebab case** format:  
     ```gradle
     .\gradlew createCategoryTemplateModule -Pcategory={CategoryName} -Pmodule={module-name}
     ```
     
5. **Add a README file to the Module**
   - Create a `README.md` file in the root of your module directory.  
   - Populate the README file with the following sections:  
     - **Link to the Selected Design**  
       Add a link to the original design (e.g., from [Dribbble](https://dribbble.com/) or another source).  
     - **Screenshots of the Implemented Design**  
       Include screenshots showcasing your implementation.  
     - **Navigation Graph of the Design**  
       - Provide a visual representation of the navigation between pages.  
       - Skip this step if the navigation is linear or involves only two pages.  
       - Tools like [Lucidchart](https://www.lucidchart.com) can help create navigation diagrams.  
     - **Demo of the Implemented Design**  
       - Add a GIF showcasing interactions within the design.  
       - If there are no interactions, this step is optional.  
     - **Key Highlights of the Design**  
       Write a brief explanation of what makes your implementation unique or notable.  
     - **Helpful Links**  
       Optionally, include links to resources that assisted you in solving challenges or implementing the design.

7. **Ensure Code Quality**  
   - Confirm that your code follows the project's style guidelines and runs without errors.

8. **Submit a Pull Request**  
   - Create a pull request to merge your branch into the **Master** branch.  
