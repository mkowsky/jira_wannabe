<template>
    <div>
        <v-expansion-panels>
            <v-expansion-panel>
                <v-expansion-panel-header>
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
            }
        }
    }
</script>

<style scoped>

</style>
