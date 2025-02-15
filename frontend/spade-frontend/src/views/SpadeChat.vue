<template>
    <div class="container">
        <h1>Welcome, {{ username }}</h1>
    </div>
</template>

<script>
    export default{
        props: ['username']
    };
</script>

<style scoped>
.container {
  text-align: center;
  margin-top: 50px;
}
</style>