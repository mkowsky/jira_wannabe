<template>
    <div>
        <v-expansion-panels :value="expanded">
            <v-expansion-panel>
                <v-expansion-panel-header >
                    {{panelTitle}}
                </v-expansion-panel-header>
                <v-expansion-panel-content>
                  <slot name="content"></slot>
                </v-expansion-panel-content>
            </v-expansion-panel>
        </v-expansion-panels>
    </div>
</template>

<script>
    export default {
        name: "ExpansionPanel",
        props: {
            panelTitle: {
                type: String,
                default: 'Title',
            },
            expanded: {
                type: Number,
            }
        }
    }
</script>

<style scoped>

</style>
