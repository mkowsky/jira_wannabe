<template>
    <body style="display: flex; flex-direction: column; justify-content: space-between; height: 100vh; background: #f8f8f8">
    <template v-if="registerSuccess">
        <div class="mailsent-wrapper">
            <div class="mailsent-claim">JIRA-WANNABE</div>
            <div class="mailsent-subclaim">Dear {{firstName}}, check your email! </div>
            <div class="mailsent-img">
                <img src="../assets/images/undraw_Mail_sent_re_0ofv.svg" style="width: 100%; height: 100%">
            </div>
            <div class="mailsent-description">We just send you a verification link. That link will expire shortly, so be sure to click on it soon to get started! </div>
            <v-btn x-large :color="colorAccent" style="width: 200px;" @click="navigateTo('login')">LOG IN</v-btn>
        </div>
    </template>
    <template v-if="!registerSuccess">
    <div style="position: absolute; left: 50%; transform: translateX(-50%); top: 70%; color: white; font-size: 16px; font-weight: 900; opacity: 0.9">
        LET'S MAKE THE WORLD MORE PRODUCTIVE, TOGETHER.
    </div>
    <div class="header">
        <ul>
            <li>
                <div class="logo-wrapper-small">
                    <img src="../assets/images/logo.png" class="logo-content">
                </div>
                <div class="logo-slogan-small">JIRA-WANNABE</div>
            </li>
            <li @click="navigateTo('')">ABOUT</li>
            <li @click="navigateTo('login')">LOGIN</li>
            <li @click="navigateTo('signup')">SIGN UP</li>
        </ul>
    </div>

    <div class="register-box">
        <div class="register-claim">Let's go!</div>

        <div style="width: 90%; align-self: center">
            <span style="font-size: 15px; font-weight: 600; opacity: 0.8;">First Name</span>
            <v-text-field solo label="Enter first name" v-model="firstName" prepend-inner-icon="mdi-account"
                          :rules="[rules.required]" style="margin-top: 5px;"></v-text-field>
        </div>

        <div style="width: 90%; align-self: center">
            <span style="font-size: 15px; font-weight: 600; opacity: 0.8;">Last Name</span>
            <v-text-field solo label="Enter last name" v-model="lastName" prepend-inner-icon="mdi-account"
                          :rules="[rules.required]" style="margin-top: 5px;"></v-text-field>
        </div>


        <div style="width: 90%; align-self: center">
            <span style="font-size: 15px; font-weight: 600; opacity: 0.8;">Email</span>
            <v-text-field solo label="Enter email" v-model="email" prepend-inner-icon="mdi-email"
                          :rules="[rules.required, rules.email]" :error-messages="emailError"
                          style="margin-top: 5px;"></v-text-field>
        </div>

        <div style="width: 90%; align-self: center">
            <span style="font-size: 15px; font-weight: 600; opacity: 0.8;">Password</span>
            <v-text-field solo label="Password" v-model="password" type="password" prepend-inner-icon="mdi-lock"
                          :rules="[rules.required]" :error-messages="passwordError"
                          style="margin-top: 5px;"></v-text-field>
        </div>

        <button class="register-button"
                v-bind:disabled="allFieldsFilled"
                @click="registerNewUser()"
                v-bind:class="{'register-button': !allFieldsFilled, 'register-button-disabled': allFieldsFilled}">Play
            with Jira-Wannabe
        </button>

    </div>


    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
        <defs>
            <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="0%">
                <stop offset="0%" style="stop-color:#FF6F91;stop-opacity:1"></stop>
                <stop offset="60%" style="stop-color:rgba(125,120,221,1);stop-opacity:1"></stop>
                <stop offset="78%" style="stop-color: rgba(110,149,228,1);stop-opacity:1"></stop>
                <stop offset="100%" style="stop-color:rgba(230,84,153,1);stop-opacity:1"></stop>
            </linearGradient>
        </defs>
        <path fill="url(#grad1)" fill-opacity="1"
              d="M0,256L34.3,224C68.6,192,137,128,206,106.7C274.3,85,343,107,411,101.3C480,96,549,64,617,42.7C685.7,21,754,11,823,21.3C891.4,32,960,64,1029,96C1097.1,128,1166,160,1234,149.3C1302.9,139,1371,85,1406,58.7L1440,32L1440,320L1405.7,320C1371.4,320,1303,320,1234,320C1165.7,320,1097,320,1029,320C960,320,891,320,823,320C754.3,320,686,320,617,320C548.6,320,480,320,411,320C342.9,320,274,320,206,320C137.1,320,69,320,34,320L0,320Z"></path>

    </svg>
    </template>
    </body>
</template>


<script>

    import axios from 'axios';
    import colors from "@/assets/css/colors"


    export default {
        name: "Register",
        components: {},
        data() {
            return {
                colorAccent: colors.ACCENT,
                registerSuccess: false,
                emailError: '',
                passwordError: '',
                message: '',

                firstName: '',
                lastName: '',
                password: '',
                email: '',


                rules: {
                    required: value => !!value || 'Required.',
                    counter: value => value.length <= 20 || 'Max 20 characters',
                    email: value => {
                        const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                        return pattern.test(value) || 'Invalid e-mail.'
                    },
                },
            }
        },
        methods: {
            navigateTo(value) {
                this.$router.push('/' + value);
            },
            registerNewUser() {
                axios.post('http://localhost:8080/api/auth/signup', {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    password: this.password,
                    email: this.email,
                }).then(response => {
                    console.log(response.status);
                    this.registerSuccess = true;
                },
                error => {
                    this.message = error.response.data.message;
                    if (this.message.includes('Email')) this.emailError = this.message;
                    else this.emailError = "";

                    this.registerSuccess = false;
                });
            },
        },
        watch: {
            email: function () {
                if (this.allFieldsFilled) this.emailError = "";
            },
            password: function () {
                if (this.allFieldsFilled) this.passwordError = "";
            },
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            },
            allFieldsFilled() {
                if ((this.email) && (this.password) && (this.firstName) && (this.lastName)) return false;
                else return true;
            }
        },
    }
</script>

<style scoped lang="scss">
    @import "../assets/css/main";



    body {
        font-family: 'Montserrat', sans-serif;
    }

    .logo-content {
        width: 70%;
        height: 70%;
        object-fit: cover;
    }

    .header {
        width: 100%;
        height: 120px;
        padding: 20px;

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
            color: $color-primary-dark;
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
            color: $color-primary-dark;
        }
    }

    .logo-wrapper-small {
        background: linear-gradient(90deg, rgba(108, 99, 255, 1) 0%, rgba(125, 120, 221, 1) 60%, rgba(92, 184, 236, 1) 100%);
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

    .register-box {
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        box-shadow: 0 0 10px black;
        width: 500px;
        height: 600px;
        top: 15%;
        background: white;
        display: flex;
        flex-direction: column;
        padding: 20px;

        border-radius: 10px;
    }

    .register-claim {
        font-size: 30px;
        font-weight: 700;
        align-self: center;
    }


    .register-button {
        background: $color-secondary-accent;
        outline: none;
        align-self: center;
        padding: 10px;
        font-size: 20px;
        width: 90%;
        color: white;
        font-weight: 800;
        margin-bottom: 10px;
    }

    .register-button-disabled {
        background: $color-secondary-accent;
        outline: none;
        align-self: center;
        padding: 10px;
        font-size: 20px;
        opacity: 0.6;
        color: white;
        font-weight: 800;
    }

    .mailsent-wrapper{
        height: 100vh;
        background: white;
        box-shadow: 0 0 10px black;
        display: flex;
        flex-direction: column;
        padding: 40px;
        align-items: center;
        justify-content: center;
    }
    .mailsent-claim{
        font-size: 50px;
        font-weight: 800;
        color: #6C63FF;
    }
    .mailsent-subclaim{
        font-size: 40px;
        opacity: 0.8;

    }

    .mailsent-img{
        width: 400px;
        height: 400px;
    }

    .mailsent-description{
        font-size: 34px;
        width: 600px;
        text-align: center;
        margin-bottom: 20px;
    }
</style>
