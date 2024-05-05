const firebaseConfig = {
    apiKey: "AIzaSyBvRdOj4JkilsOQHaR7-965emFZwmduLbY",
    authDomain: "wepapp-9948f.firebaseapp.com",
    projectId: "wepapp-9948f",
    storageBucket: "wepapp-9948f.appspot.com",
    messagingSenderId: "346264220109",
    appId: "1:346264220109:web:43216e48e4354a4d9ad484",
    measurementId: "G-8HC5VEGJPM",
  };
  firebase.initializeApp(firebaseConfig);
  var image ;
  // firebase bucket name
  // REPLACE WITH THE ONE YOU CREATE
  // ALSO CHECK STORAGE RULES IN FIREBASE CONSOLE
  var fbBucketName = "images";
  
  // get elements
  var uploader = document.getElementById("uploader");
  var fileButton = document.getElementById("product_image");
  
  // listen for file selection
  fileButton.addEventListener("change", function (e) {
    //Get files
    image = [];
    for (var i = 0; i < e.target.files.length; i++) {
      var imageFile = e.target.files[i];
  
      uploadImageAsPromise(imageFile);
    }
  });
  
  //Handle waiting to upload each file using promise
  function uploadImageAsPromise(imageFile) {
    return new Promise(function (resolve, reject) {
      var storageRef = firebase
        .storage()
        .ref(fbBucketName + "/" + imageFile.name);
  
      //Upload file
      var task = storageRef.put(imageFile);
  
      //Update progress bar
      task.on(
        "state_changed",
        function progress(snapshot) {
          var percentage =
            (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
          uploader.value = percentage;
        },
        function error(err) {},
        function complete() {
          var downloadURL = task.snapshot.downloadURL;
          console.log("dowload URL --->", downloadURL);
          image.push(downloadURL);
          let divLocation = document.getElementById("list_images");
          let imgElement = document.createElement("img");
          imgElement.src = downloadURL;
          imgElement.width = "50";
          divLocation.append(imgElement);
        }
      );
    });
  }
  function getImage() {
    return image;
  }
  