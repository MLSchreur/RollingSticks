/* ----------------------------------------------------------------------------------- */
/* Loading and resizing a new                                                          */
function loadNewImage($event) {
  // console.log(event);
  // console.log(event.target.files[0]);
  var file = event.target.files[0];
  let reader = new FileReader();
  let image = new Image();
  let cnvsEl = document.getElementById("canvasNew");
  let ctx = cnvsEl.getContext("2d");
  let encoded;
  let dataURL;

  reader.onload = function(e) {
    var src = file.name;
    // console.log(src);
    // console.log("e.target.result");
    // console.log(e.target.result);
    image.src = e.target.result;
    // console.log("width=" + image.width + "  height:" + image.height)

    image.onload = function () {
      // console.log("width=" + image.width + "  height:" + image.height)
      cnvsEl.width = image.width;
      cnvsEl.height = image.height;
      ctx.drawImage(image, 0, 0, cnvsEl.width, cnvsEl.height );
      ctx.stroke();

      dataURL = cnvsEl.toDataURL("image/png");
      encoded = window.btoa(dataURL);
      // console.log("Image size is " + encoded.length + " byte");
      
      while (encoded.length > 2400000) {
        newHeight *= 0.95;
        newWidth *= 0.95;
        cnvsEl.width = newWidth;
        cnvsEl.height = newHeight;
        ctx.drawImage(image, 0, 0, cnvsEl.width, cnvsEl.height );
        ctx.stroke();

        dataURL = cnvsEl.toDataURL("image/png");
        encoded = window.btoa(dataURL);
        // console.log("Reducing image size. New size is " + encoded.length + " byte");
      }  
      //console.log(encoded);

      var xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 202) {
          console.log("posted successfully")
        }
      };
      xhttp.open("POST", "http://localhost:8081/api/bladmuziek/1/img", true);
      xhttp.setRequestHeader("Content-type", "text/plain");
      xhttp.send(encoded);


    }
  };
  reader.readAsDataURL(event.target.files[0]);
}

/* ----------------------------------------------------------------------------------- */
/* Loading and resizing a new                                                          */
function loadNewMP3($event) {
  console.log(event);
  console.log(event.target.files[0]);
  var file = event.target.files[0];
  let reader = new FileReader();
  let dataURL;

  reader.onload = function(e) {
    var src = file.name;
    console.log(src);
    console.log("e.target.result");
    //console.log(e.target.result);

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 202) {
        console.log("posted successfully")
      }
    };
    xhttp.open("POST", "http://localhost:8081/api/bladmuziek/1/mp3", true);
    xhttp.setRequestHeader("Content-type", "text/plain");
    xhttp.send(e.target.result);
  };
  reader.readAsDataURL(event.target.files[0]);
}


function playAudio(obj) {
  var audio = document.getElementById("player");
  // console.log(audio);
  // console.log(obj);
  // console.log(obj.id);
  if(audio.paused) {
    audio.play();
    document.getElementById("imgPlay").src = "media/control_pause.png";
  } else {
    audio.pause();
    document.getElementById("imgPlay").src = "media/control_play.png";
  }
}