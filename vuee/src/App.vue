<template>
  <div id="app">
    <div id="test">Welcome to Jira-wannabe-demo-app</div>
    <p><router-link to="/test">TEMPLATE-VIEW</router-link></p>
    <router-view />
  </div>


</template>

<script>

export default {
  name: 'App',
  components: {
  }
}
</script>

<style>
  #app{
    text-align: center;
  }
  #test {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    font-size: 40px;
    color: #2c3e50;

    margin-top: 60px;
  }
</style>
