RecipeNest – Android Recipe Discovery App
📱 Module : Mobile App Development (CIS4034-N)

Name: Poojitha Mendem
Student Number: s3525839

📌 Project Overview

RecipeNest is a native Android application that allows users to discover recipes based on available ingredients, view detailed cooking instructions, and save favourite recipes for offline access.
The application integrates third-party APIs, Firebase Authentication, and local data persistence while following Agile (Scrum-inspired) development practices.

🎯 Key Features

Splash screen and onboarding flow

User authentication using Firebase (Login / Signup)

Ingredient-based recipe search via REST API

Recipe listing with images

Detailed recipe view (ingredients and instructions)

Save and view favourite recipes (offline support)

Basic user profile display (username & email)

🛠️ Technologies Used

Language: Java / Kotlin

UI: Jetpack Compose / XML

Architecture: Modular / MVVM-inspired

Authentication: Firebase Authentication

API Integration: REST API (Recipe data)

Local Storage: Room / DataStore

Version Control: Git & GitHub

📂 Project Structure
RecipeNest/
│── app/
│   ├── activities/
│   ├── adapters/
│   ├── models/
│   ├── database/
│   ├── network/
│   ├── ui/
│   └── utils/
│
└── README.md

🚀 Setup Instructions

Clone the repository:

git clone https://github.com/your-username/RecipeNest.git


Open the project in Android Studio

Sync Gradle dependencies

Add your Firebase configuration file:

google-services.json

Run the app on an emulator or physical device

🧪 Testing

Manual testing performed on Android Emulator

UI tested for responsiveness and navigation flow

Authentication and API error handling validated

📋 Agile Development

The project was developed using a Scrum-inspired Agile approach across 5 sprints:

Sprint planning and backlog managed using Trello

Regular Git commits aligned with sprint goals

Continuous feedback incorporated during development

🔐 Security & Privacy

Firebase Authentication for secure login

Encrypted network communication (HTTPS)

Minimal personal data collection (email & username)

No sensitive data stored locally

📈 Future Enhancements

Camera-based ingredient detection

Recipe sharing functionality

Push notifications

Enhanced user profile features

📜 License & Acknowledgements

Recipe data provided via third-party API

Firebase services by Google

Developed for academic purposes only