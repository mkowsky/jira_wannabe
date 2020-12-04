<template>
    <v-dialog
            v-model="dialog"
            :width="width"
            persistent
    ><v-card>
        <v-card-title class="headline">
            {{dialogTitle}}
        </v-card-title>
        <v-card-text>{{dialogContent}}</v-card-text>
        <v-card-text><slot name="body"></slot></v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn  v-show="!oneButton"
                    color="#424242"
                    text
                    @click="cancel"
            >
                {{cancelButton}}
            </v-btn>
            <v-btn
                    color="#424242"
                    text
                    @click="agree"
            >
               {{agreeButton}}
            </v-btn>
        </v-card-actions>
    </v-card>
    </v-dialog>


</template>

<script>
    export default {
        name: "Modal",
        props:{
            oneButton:{
                default: false,
            },
            dialog: {
                default: false,
            },
            dialogContent:{
                required: true,
                default: "Default",
            },
            dialogTitle:{
                required: true,
                default: "Dialog title",
            },
            agreeButton:{
                default: "Confirm"
            },
            cancelButton:{
                default: "Cancel"
            },
            width:{
                default: 500,
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
