# Elytis AR – An AR-Inspired Poetic Experience

An Android application that delivers an augmented reality inspired poetic experience, combining:

- Live camera background
- Transparent visual overlays
- Synchronized poetic text scrolling
- Narrated audio performance

The application presents a multimedia interpretation of poetry through motion, sound, and layered imagery.

---

## 🎥 Demo

> A short demonstration of the application in action.


[Watch the Demo Video](eleni.mp4)

---

## 📱 Features

- 📷 Live Camera Preview (CameraX)
- 🖼 Transparent Image Overlays
- 🎵 Synchronized Audio Narration (MediaPlayer)
- 📜 Auto-scrolling poetic text
- 🌊 Immersive full-screen landscape presentation
- ✨ Fade-in / Fade-out image transitions
- 🔆 Screen stays active during performance

---

## 🏗 Architecture

- **Language:** Java
- **IDE:** Android Studio
- **Camera API:** CameraX
- **Media:** MediaPlayer
- **UI:** XML Layout with layered FrameLayout
- **Synchronization:** Handler-based timed updates

---

## 🎭 Concept

The application explores the relationship between:

- Nature (real-world camera background)
- Memory (transparent visual layers)
- Voice (narration)
- Poetry (synchronized text movement)

It creates an immersive AR-inspired experience without requiring external AR frameworks.

---

## 📂 Project Structure

elytis_ar/
│
├── app/
│ ├── java/com/example/elytis_ar/
│ │ └── ARPoemActivity.java
│ ├── res/
│ │ ├── layout/activity_ar_poem.xml
│ │ ├── drawable/
│ │ └── raw/
│ │ ├── eleni.mp3
│ │ └── poem.txt
│ └── AndroidManifest.xml
│
└── README.md


---

## 🚀 How to Run

1. Clone the repository:

git clone https://github.com/PanagiotisBeligiannis/elytis_ar.git


2. Open in Android Studio
3. Allow camera & audio permissions
4. Run on a physical Android device (recommended)

---

## 📌 Academic Context

This project was developed as part of an academic assignment exploring:

- Multimedia interaction
- Mobile AR concepts
- Real-time media synchronization
- Poetic digital storytelling

---

## 👨‍💻 Author

Panagiotis Beligiannis

---

## 📜 License

MIT License
This project is for academic and educational purposes.

