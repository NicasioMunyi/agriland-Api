Spring boot
MySQL
JPA
Rest API

ENDPINTS

USERS
POST  /user/addUser -->Adding new users
GET   /user/getUserById/{id} --> Get user by Id
    {"id":1,"name":"Nick Alfred","email":"alfred@gmail.com","password":"alfred200","security":"question1","answer":"red","image":"IMG_20190522_231226.jpg_1707841024173.jpg"}
GET   /user/getAllUsers --> Get user All Users
    [
      {"id":1,"name":"Nick Alfred","email":"alfred@gmail.com","password":"alfred200","security":"question1","answer":"red","image":"IMG_20190522_231226.jpg_1707841024173.jpg"},
      {"id":2,"name":"nicasio Mumunyi","email":"mugendi@gmail.com","password":"admin","security":"security question","answer":"red","image":"profile.png_1701524832507.jpg"},
      {"id":52,"name":" alfred","email":"nickalfred@gmail.com","password":"alfred","security":"question2","answer":"embu","image":"181467093_1397922997225165_6487345646506871477_n.jpg_1704278597072.jpg"}
    ]

PUT /user/updateProfile/{id} --> Update User Profile
GET /user/getUserByEmail/{email} --> Get user By email

POSTS

POST /addNewPost  -->Add New Post
GET /getAllPosts --> Get All Posts
{
    "id": 1,
    "name": "Nick Alfred",
    "email": "alfred@gmail.com",
    "password": "alfred200",
    "security": "question1",
    "answer": "red",
    "image": "IMG_20190522_231226.jpg_1707841024173.jpg"
}

IMAGES

POST /images/upload  --> upload an Image file
GET /images/allImages --> Get all Images
GET /Images/{imageName:.+} --> Get Image by name.extension
