<template>
    <body style="display: flex; flex-direction: column">

    <div class="header">
        <ul>
            <li>
                <div class="logo-wrapper-small">
                    <img src="../assets/logo.png" class="logo-content">
                </div>
                <div class="logo-slogan-small">JIRA-WANNABE</div>
            </li>
            <li>ABOUT</li>
            <li @click="navigateTo('login')">LOGIN</li>
            <li>SIGN UP</li>
        </ul>
    </div>

    <div class="header2">
        <div class="header2-text-content">
            <div class="claim">Project management without all the management.</div>
            <div class="subclaim">Jira-Wannabe is collaborative project management app that streamline and refines your
                workflow. Managing your tasks and projects never been easier.
            </div>
            <v-btn x-large rounded class="trial-button" color="#6C63FF">START FREE TRIAL</v-btn>
        </div>
        <div class="header2-logo-content">
            <div class="logo-wrapper-big">
                <img src="../assets/logo.png" class="logo-content">
            </div>
            <div class="logo-slogan-big">JIRA-WANNABE</div>
        </div>


    </div>

    <div class="content">
        <div class="content-text">
            <div class="sample-active" @click="changeText(1)"><span id="1" class="span-inactive">01.</span>Slash
                Commands
            </div>
            <div class="sample" @click="changeText(2)"><span id="2" class="span-inactive">02.</span>Real-time Chat</div>
            <div class="sample" @click="changeText(3)"><span id="3" class="span-inactive">03.</span>Task Tray</div>
            <div class="sample" @click="changeText(4)"><span id="4" class="span-inactive">04.</span>Assign Comments
            </div>
            <div class="sample" @click="changeText(5)"><span id="5" class="span-inactive">05.</span>Inbox</div>
        </div>
        <div class="photo-wrp">

                <v-carousel v-model="model" :show-arrows="false" hide-delimiters>
                    <v-carousel-item

                            v-for="color in colors"
                            :key="color"
                    >
                        <v-sheet
                                :color="color"
                                height="80%"
                                tile
                        >
                            <v-row
                                    class="fill-height"
                                    align="center"
                                    justify="center"
                            >
                                <div class="display-3">
                                </div>
                            </v-row>
                        </v-sheet>
                    </v-carousel-item>
                </v-carousel>


            <div class="photo-description">
            </div>
            <div class="nav">
                <div class="-bar -bar-active" id="bar-1"></div>
                <div class="-bar -bar-inactive" id="bar-2"></div>
                <div class="-bar -bar-inactive" id="bar-3"></div>
                <div class="-bar -bar-inactive" id="bar-4"></div>
                <div class="-bar -bar-inactive" id="bar-5"></div>
            </div>
        </div>
    </div>

    <div class="footer">

    </div>

    </body>
</template>

<script>

    export default {
        name: "Home",
        components: {},
        data() {
            return {
                textDescription: '',
                previousBar: 1,
                model: 0,
                colors: [
                    'primary',
                    'secondary',
                    'yellow darken-2',
                    'red',
                    'orange',
                ],
                texts:[
                    'Do everything without ever clicking your mouse. Just type / when creating or editing tasks.',
                    'Keep conversations regarding tasks, projects, or anything relevant, convenient and focused! Include attachments, links, emojis, and more.',
                    'Keep tasks minimized in your tray that you\'re actively working on and you\'ll never open a new tab again',
                    'If your comment requires action, assign it! Creating an assigned comment means nothing will fall through the cracks',
                    'Use Inbox to see what you have to work on next.',
                ]
            }
        },
        methods: {
            navigateTo(value){
                this.$router.push('/'+value);
            },
            changeText(value) {
                document.getElementById(this.previousBar).classList.remove('span-active');
                document.getElementById(this.previousBar).parentElement.classList.remove('sample-active');
                document.getElementById('bar-' + this.previousBar).classList.remove('-bar-active');
                document.getElementById('bar-' + this.previousBar).classList.add('-bar-inactive');
                document.getElementById(this.previousBar).parentElement.classList.add('sample');
                document.getElementById('bar-' + value).classList.remove('-bar-inactive');
                document.getElementById('bar-' + value).classList.add('-bar-active');
                document.getElementById(value).classList.add('span-active');
                document.getElementById(value).parentElement.classList.add('sample-active');
                this.previousBar = value;
                this.model=value-1;
                this.textDescription.innerHTML = this.texts[value-1];
            },
        },
        mounted(){
            this.textDescription = document.querySelector('.photo-description');
            this.textDescription.innerHTML = this.texts[0];
        }
    }
</script>

<style scoped lang="scss">
    $color-primary: white;
    $color-main-accent: #424242;
    $color-secondary-accent: #6C63FF;
    @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700;800&display=swap');

    body {
        font-family: 'Montserrat', sans-serif;
    }

    .header {
        width: 100%;
        height: 120px;
        padding: 20px;

    }

    .claim {
        box-decoration-break: clone;
        font-size: 58px;
        font-weight: 800;
        width: 1000px;
        text-align: center;
        opacity: 0.9;

    }

    .subclaim {
        font-size: 36px;
        font-weight: 500;
        width: 1000px;
        opacity: 0.7;
        text-align: center;
    }

    .photo-wrp {
        box-shadow: 0 0 10px black;
        background: white;
        height: 600px;
        width: 800px;
        overflow: hidden;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 30px;

        /*margin: auto;*/
    }



    .photo-description {
        font-weight: 500;
        font-style: italic;
        font-size: 22px;
        text-align: center;
        margin-bottom: 10px;
        margin-top: 10px;
    }

    .header2 {
        width: 100%;
        height: 80vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .header2-text-content {
        width: 60%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-between;
        height: 58%;
    }

    .header2-logo-content {
        width: 30%;
        display: flex;
        justify-content: space-between;
        flex-direction: column;
        align-items: center;
    }

    .trial-button {
        width: 400px;
    }

    .content {
        width: 100%;
        height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .content-text {
        display: flex;
        flex-direction: column;
        width: 30%;
    }

    .footer {
        width: 100%;
        height: 20vh;
        background: purple;
    }

    .sample {
        width: 450px;
        padding: 15px 0 15px 20px;
        font-size: 32px;
        font-weight: 800;
        color: #424242;
        opacity: 0.4;
        cursor: pointer;

    }

    .sample:hover {
        opacity: 0.8;
    }

    .sample-active {
        width: 450px;
        padding: 15px 0 15px 20px;
        font-size: 32px;
        font-weight: 800;
        color: #424242;
        border-left: 10px solid purple;
        opacity: 1;
        cursor: pointer;
        transition: 0.3s;
    }

    .span-inactive {
        margin-right: 20px;
        transition: 0.3s;
    }

    .span-active {
        color: purple;
        margin-right: 20px;
        transition: 0.3s;
    }

    ul {
        display: flex;
        list-style: none;
        justify-content: flex-end
    }

    ul li {
        font-size: 30px;
        margin-right: 100px;
        cursor: pointer;
        font-weight: 500;

        &:hover {
            color: $color-main-accent;
        }
    }

    ul li:first-of-type {
        margin-right: auto;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: auto;

        &:hover {
            color: black;
        }
    }

    ul li:last-of-type {
        color: $color-secondary-accent;
        font-weight: 800;

        &:hover {
            color: $color-main-accent;
        }
    }

    .logo-wrapper-small {
        background: $color-secondary-accent;
        width: 60px;
        height: 60px;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 10px;
    }

    .logo-wrapper-big {
        background: $color-secondary-accent;
        width: 400px;
        height: 400px;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 10px;
    }

    .logo-slogan-small {
        font-size: 30px;
        font-weight: 600
    }

    .logo-slogan-big {
        font-size: 60px;
        font-weight: 800;
    }

    .logo-content {
        width: 70%;
        height: 70%;
        object-fit: cover;
    }

    .nav {
        width: 100%;
        height: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .-bar {
        width: 10px;
        height: 10px;
        border-radius: 50%;
        margin-right: 10px;
        background: black;
    }

    .-bar-active {
        background: purple;
        transition: 0.3s;
    }

    .-bar-inactive {
        background: black;
        opacity: 0.5;
        transition: 0.3s;
    }
</style>
