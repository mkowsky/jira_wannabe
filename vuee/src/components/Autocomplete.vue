<template>
    <div class="autocomplete">
        <div class="input" @click="toggleVisible"></div>
        <div class="popover" v-show="visible">
            <input type="text"
                   v-model="query"
                   :placeholder="placehold">

            <div v-if="!gridDisplay" class="options">
                <ul>
                    <li v-for="(item, index) in mathces"
                        :key="item.id"
                        @click="listItemClicked(index, item.firstName + ' ' + item.lastName, item.id)">{{item.firstName}} {{item.lastName}}
                    </li>
                </ul>
            </div>
        </div>

        <div v-if="gridDisplay" style="position: relative; top: 100px;">

            <div class="grid-wrapper">
                <div class="grid-item"
                     v-for="(item, index) in mathces"
                     :key="item.id"
                     @click="gridItemClicked(index, item.id)"
                >
                    <Task
                            v-bind:title="item.name"
                            v-bind:description="item.description"
                            v-bind:project-manager="item.taskManager.username"></Task>
                </div>

            </div>


        </div>

    </div>
</template>

<script>
    import Task from "@/components/Task";

    export default {
        name: "Autocomplete",
        components: {Task},
        props: {
            items: {

            },
            filterby: {
                type: String
            },
            gridDisplay: {
                type: Boolean,
                default: false,
            },
            placehold: {
                type: String,
            }
        },
        data() {
            return {
                visible: false,
                query: "",

            }
        },
        methods: {
            toggleVisible() {
                this.visible = !this.visible
            },
            gridItemClicked(index, item) {
                this.$emit('grid-item-clicked', item);
            },
            listItemClicked(index, item, id){
                this.$emit('list-item-clicked', item, id)
            }
        },
        computed: {
            mathces() {
                if (this.query === '') {
                    return [];
                }
                return this.items.filter((item) => item[this.filterby].toLowerCase().includes(this.query.toLowerCase()))
            }
        },
        created() {
            console.log(this.items);
        }
    }
</script>

<style scoped>
    .autocomplete {
        width: 100%;
        position: relative;
    }

    .input {
        height: 40px;
        border-radius: 3px;
        border: 2px solid lightgray;
        box-shadow: 0 0 10px #eceaea;
        font-size: 25px;
        padding-left: 10px;
        padding-top: 10px;
        cursor: text;
    }

    .close {
        position: absolute;
        right: 2px;
        top: 4px;
        background: none;
        border: none;
        font-size: 30px;
        color: lightgrey;
        cursor: pointer;
    }

    .placeholder {
        position: absolute;
        top: 11px;
        left: 11px;
        font-size: 25px;
        color: #d0d0d0;
        pointer-events: none;
    }

    .popover {
        min-height: 50px;
        border: 2px solid lightgray;
        position: absolute;
        top: 46px;
        left: 0;
        right: 0;
        background: #fff;
        border-radius: 3px;
        text-align: center;
    }

    .popover input {
        width: 95%;
        margin-top: 5px;
        height: 40px;
        font-size: 16px;
        border-radius: 3px;
        border: 1px solid lightgray;
        padding-left: 8px;
    }

    .options {
        max-height: 150px;
        overflow-y: scroll;
        margin-top: 5px;
    }

    .options ul {
        list-style-type: none;
        text-align: left;
        padding-left: 0;
    }

    .options ul li {
        border-bottom: 1px solid lightgray;
        padding: 10px;
        cursor: pointer;
        background: #f1f1f1;
    }

    .options ul li:first-child {
        border-top: 2px solid #d6d6d6;
    }

    .options ul li:not(.selected):hover {
        background: #8c8c8c;
        color: #fff;
    }

    .options ul li.selected {
        background: #58bd4c;
        color: #fff;
        font-weight: 600;
    }

    .grid-wrapper {
        position: relative;
        top: 30%;
        width: 50%;
        left: -50%;

        margin-left: auto;
        display: grid;
        grid-template-columns: 300px 300px 300px 300px;
        grid-template-rows: 200px 200px;
        grid-column-gap: 30px;
        grid-row-gap: 40px;
    }

    .grid-item {

    transition-duration: 0.3s;
    transition-property: transform;
    }

    .grid-item:hover,
    .grid-item:focus,
    .grid-item:active{
        cursor: pointer;
    box-shadow: 0 15px 25px rgba(255, 255, 255, .9);
    transform: scale(1.1);
    }

</style>
