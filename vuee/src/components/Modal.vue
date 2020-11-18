<template>
    <v-dialog
            v-model="dialog"
            width="500"
            persistent
    ><v-card>
        <v-card-title class="headline">
            Usun komentarz
        </v-card-title>
        <v-card-text>Czy jesteś pewny, że chcesz usunać komentarz?</v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="green darken-1"
                    text
                    @click="cancel"
            >
                Nie
            </v-btn>
            <v-btn
                    color="green darken-1"
                    text
                    @click="agree"
            >
                Tak
            </v-btn>
        </v-card-actions>
    </v-card>
    </v-dialog>

    <!--<div class="modal-backdrop">-->
        <!--<div class="modal">-->
            <!--<header class="modal-header">-->
                <!--<slot name="header">-->
                    <!--This is the default tile!-->
                <!--</slot>-->

            <!--</header>-->
            <!--<section class="modal-body">-->
                <!--<slot name="body">-->

                <!--</slot>-->
            <!--</section>-->

            <!--<footer class="modal-footer">-->
                <!--<slot name="footer">-->
                    <!--<button-->
                            <!--type="button"-->
                            <!--class="btn-green"-->
                            <!--@click="close">Close me!-->
                    <!--</button>-->
                <!--</slot>-->
            <!--</footer>-->

        <!--</div>-->
    <!--</div>-->
</template>

<script>
    export default {
        name: "Modal",
        props:{
            dialog: {
                default: false,
            }
        },
        methods:{
            close(){
                this.$emit('close-modal')
            },
            cancel(){
                this.$emit('modal-cancel');
            },
            agree(){
                this.$emit('modal-agree');
            }
        }
    }
</script>

<style scoped>
    .modal-backdrop {
        position: fixed;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background-color: rgba(0, 0, 0, 0.3);
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .modal {
        background: #FFFFFF;
        box-shadow: 2px 2px 20px 1px;
        overflow-x: auto;
        display: flex;
        flex-direction: column;
        z-index: 100;
    }

    .modal-header,
    .modal-footer {
        padding: 15px;
        display: flex;
    }

    .modal-header {
        border-bottom: 1px solid #eeeeee;
        color: #4AAE9B;
        justify-content: space-between;
    }

    .modal-footer {
        border-top: 1px solid #eeeeee;
        justify-content: flex-end;
    }

    .modal-body {
        position: relative;
        padding: 20px 10px;
    }


    .btn-green {
        color: white;
        background: #4AAE9B;
        border: 1px solid #4AAE9B;
        border-radius: 2px;
    }
</style>
