<template>
    <b-container class="webcam-component">
        <b-row>
            <b-col id="record">
                <div>
                    <video ref="video" id="video" width="100%" height="auto" autoplay></video>
                </div>
                <div>
                    <b-button id="snap" variant="primary" v-on:click="takePicture()">Mache ein Bild von dir!</b-button>
                    <br>
                    <span class="dsgvo">Mit Deinem Klick akzeptierst Du unsere Datenschutzbestimmungen</span>
                </div>
            </b-col>
            <b-col id="picture">
                <div>
                    <canvas ref="canvas" id="canvas" :width="canvasWidth" :height="canvasHeight"></canvas>
                </div>
                <div>
                    <b-button id="record" variant="primary" v-on:click="activateCam()">Neue Aufnahme</b-button>
                </div>
                <klapper :data="output" :table="tabelle"></klapper>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
    import Klapper from './klapper.vue'

    export default {
        name: 'Webcam',
        components: {
            Klapper
        },
        props: {
            msg: String
        },
        data() {
            return {
                video: {},
                canvas: {},
                image: {},
                output: {},
                tabelle: {},
                canvasHeight: 0,
                canvasWidth: 0
            }
        },
        methods: {
            takePicture() {
                this.canvas = this.$refs.canvas;
                this.canvas.getContext("2d").drawImage(this.video, 0, 0, this.canvasWidth, this.canvasHeight);
                this.image = this.canvas.toBlob(this.sendPicture, "image/jpeg");
                document.getElementById('picture').style.display = "block";
                document.getElementById('record').style.display = "none";
            },
            activateCam() {
                document.getElementById('record').style.display = "block";
                document.getElementById('picture').style.display = "none";
                let currentObj = this;
                currentObj.$emit("cocktailFound", {namne: null});
                currentObj.output = {name: null};
                this.calculateCanvas();
            },
            sendPicture(picture) {
                let currentObj = this;
                const formData = new FormData();
                formData.append('picture', picture);
                this.axios({
                    method: 'post',
                    url: 'https://bartender.bohni.net/cocktailForImage',
                    data: formData,
                    config: {headers: {'Content-Type': 'multipart/form-data'}}
                })
                    .then(function (response) {
                        currentObj.output = response.data;
                        currentObj.$emit("cocktailFound", response.data);
                        currentObj.createTable(response.data);
                    })
                    .catch(function (error) {
                        currentObj.output = error;
                    });
            },
            createTable(data) {
                let rekognitionResult = data.rekognitionResult;
                this.tabelle.Alter = rekognitionResult.age;
                this.tabelle.Geschlecht = rekognitionResult.faceList[0].gender.value == "Male" ? "Männlich" : "Weiblich";
                this.tabelle.Lachen = rekognitionResult.faceList[0].smile.value ? "Ja" : "Nein";
                this.tabelle.Brille = rekognitionResult.faceList[0].eyeglasses.value ? "Ja" : "Nein";
                this.tabelle.Augen_offen = rekognitionResult.faceList[0].eyesOpen.value ? "Ja" : "Nein";
                this.tabelle.Mund_offen = rekognitionResult.faceList[0].mouthOpen.value ? "Ja" : "Nein";
                this.tabelle.Bart = rekognitionResult.faceList[0].beard.value ? "Ja" : "Nein";

            },
            calculateCanvas() {
                this.canvasHeight = this.video.offsetHeight;
                this.canvasWidth = this.video.offsetWidth;
            }
        },
        mounted() {
            this.video = this.$refs.video;
            if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
                navigator.mediaDevices.getUserMedia({video: true}).then(stream => {
                    this.video.srcObject = stream;
                    this.video.play();
                });
            }
            this.calculateCanvas();
        }
    }
</script>
<style>
    #video {
        background-color: #000000;
    }

    #picture {
        display: none;
    }

    div .btn {
        font-size: 1.5rem;
        margin: 5px;
    }

    .dsgvo {
        color: white;
        text-shadow: 3px 3px 4px black;
    }

</style>