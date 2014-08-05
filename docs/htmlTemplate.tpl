<!doctype html>
<html class="no-js" lang="en">
<head>
    <meta charset="utf-8">
    <title>ChangeLog - {{title}}</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>

<h1>{{title}}</h1>
{{#title_chars}}={{/title_chars}}

{{#versions}}
{{label}}
{{#label_chars}}-{{/label_chars}}

<h2>Sections</h2>
{{#sections}}
<strong>{{label}}</strong>
{{#label_chars}}~{{/label_chars}}

<h2>Commits</h2>
{{#commits}}
<p class="commit">{{subject}}
<span class="author">[{{author}}]</span></p>


{{#body}}
{{body_indented}}
{{/body}}

{{/commits}}
{{/sections}}
{{/versions}}
</html>

