<template>
    <body>
    <div class="container">

        <Logo id="outer-circle"></Logo>

        <div class="login-box">
            <div class="input-wrapper">
                <div v-if="emailError" class="input-error">{{emailError}}</div>
                <font-awesome-icon icon="user" class="icon"/>
                <input class="my-input-field"
                       type="text"
                       placeholder="Email"
                       v-model="email">
            </div>


            <div class="input-wrapper">
                <div v-if="passwordError" class="input-error">{{passwordError}}</div>
                <font-awesome-icon icon="lock" class="icon"/>
                <input class="my-input-field"
                       type="password"
                       placeholder="Password"
                       v-model="password">
            </div>

            <button class="login-button"
                    v-bind:disabled="allFieldsFilled"
                    @click="startLoginProcedure()"
                    v-bind:class="{'login-button': !allFieldsFilled, 'login-button-disabled': allFieldsFilled}">LOGIN
            </button>

            <div class="password-renew">Forget username/password?</div>

        </div>
    </div>
    </body>
</template>


<script>

    //TODO: error na polu to ramka inputu zmienia kolor?
    //import axios from 'axios';
    import User from '../model/user';
    import Logo from "@/components/Logo";

    export default {
        name: "Login",
        components: {Logo},
        data() {
            return {
                disabled: true,
                successful: false,

                message: "",


                email: '',
                password: '',

                emailError: "",
                passwordError: "",
            }
        },
        methods: {
            startLoginProcedure: function () {
                console.log('login button clicked');
                this.sendRequestToBackend()
            },
            sendRequestToBackend: function () {
                console.log(this.email);
                let user = new User(this.email, null, this.password);
                this.$store.dispatch('auth/login', user).then(
                    response => {
                        console.log(response.status);
                        this.$router.push('/board');
                    },
                    error => {
                        this.message = error.response.data.message;
                        console.log(error.response.data.message);
                        if (this.message.includes('Username')) this.emailError = this.message;
                        else this.emailError = "";

                        if (this.message.includes('password')) this.passwordError = this.message
                        else this.passwordError = "";
                        this.successful = false;
                    }
                )
            }
        },
        watch: {
            email: function () {
                if (this.allFieldsFilled) this.emailError = "";
            },
            password: function () {
                if (this.allFieldsFilled) this.passwordError = "";
            }
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            },
            allFieldsFilled() {
                if ((this.email) && (this.password)) return false;
                else return true;
            }
        },
        created() {
            if (this.loggedIn) {
                //this.$router.push('/home');
            }
        }
    }
</script>

<style scoped>


    #container {
        top: 0;
        left: 0;
        position: absolute;
        width: 100%;
        height: 100%;
        background-color: #1A1A1D;

    }

    #outer-circle {
        position: absolute;
        top: 5%;
        left: 50%;
        transform: translateX(-50%);
        z-index: 300;
    }



    .login-box {
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        background-color: #A29C9B;
        width: 500px;
        height: 400px;
        top: 25%;
        border-radius: 20%;

    }

    .input-wrapper {
        position: relative;
        top: 38%;
        margin-bottom: 30px;

    }

    .login-button {
        left: 50%;
        transform: translateX(-50%);
        position: fixed;

        bottom: 60px;
       background: lightpink;
        outline: none;
        color: black;
        border: 1px solid white;
        padding: 10px 80px;
        border-radius: 15px;
        font-weight: lighter;
        font-family: 'Courier New', Courier, monospace;
        font-size: 20px;
        letter-spacing: 5px;

    }

    .login-button-disabled {
        left: 50%;
        transform: translateX(-50%);
        position: fixed;
        bottom: 60px;

        background: lightpink;
        opacity: 0.3;
        outline: none;
        color: black;
        border: 1px solid white;
        padding: 10px 80px;
        border-radius: 15px;
        font-weight: lighter;
        font-family: 'Courier New', Courier, monospace;
        font-size: 20px;
        letter-spacing: 5px;

    }

    .password-renew {
        position: fixed;
        bottom: 20px;
        left: 50%;
        transform: translateX(-50%);
        font-family: 'Courier New', Courier, monospace;
        font-size: 14px;
        letter-spacing: 1px;
    }

    .password-renew:hover {
        color: white;
        cursor: pointer;

    }

    .input-error {
        position: absolute;
        top: -20px;
        left: 50%;
        transform: translateX(-50%);
        font-size: 18px;
        font-weight: bold;
        font-family: 'Courier New', Courier, monospace;
        color: crimson;
    }




    .icon {
        position: absolute;
        margin: 12px;
        color: black;
        min-width: 60px;
        text-align: center;

    }

    .my-input-field {

        background: white;
        height: 30px;
        font-family: 'Courier New', Courier, monospace;
        font-size: 20px;
        letter-spacing: 3px;
        width: 300px;
        padding: 5px;
        text-align: center;

    }

</style>
