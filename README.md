[![FabricAPI](https://img.shields.io/static/v1?label=modloader&message=fabric&color=brightgreen)](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
![Mod Environment](https://img.shields.io/static/v1?label=environment&message=server&color=yellow)
[![License](https://img.shields.io/static/v1?label=licence&message=GPL-3.0&color=blue)](./LICENSE)

[<img alt="Requires Fabric API" src="https://i.imgur.com/Ol1Tcf8.png" width="128"/>](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
 
 # Telegram-Minecraft

_Fabric server mod for communicating with your friends in minecraft via telegram chat_

___
# configure your mod
1. you need register a telegram bot via [BotFather](https://t.me/BotFather)
2. Change the bot's privacy settings in Botfather: 1) /setprivacy 2) choose your bot 3) Disable
3. for step 3, you need add the created bot to the chat with your friends :)
4. next, you must configure the bot in minecraft, to do this, enter the command /SetBot "your_bot_token" (you must have operator rights)
5. get the id of your telegram chat, to do this, type in your telegram chat /get_chat
6. specify your chat in minecraft, to do this, enter the command /SetChat your_telegram_chat_id (you must have operator rights)
   #### fast-create-tgbot
   
to make it easier to create a bot for Telegram-Minecraft mod you can use [script written in python](https://github.com/kibersportovich/Telegram-Minecraft-scripts)
___
# Registration
you can link your telegram profile to your nickname in minecraft: in your bot type /reg
<p>enter the resulting code in minecraft: /reg your_code</p>
 
_your nick color in chat without registration_ - ![#5555FF](https://placehold.co/15x15/5555FF/5555FF.png) 

_with registration_ - ![#55FF55](https://placehold.co/15x15/55FF55/55FF55.png)
# all commands
* */get_chat* - get the id of this telegram chat 
* */SetBot* _"your_bot_token"_ - Set bot token
* */SetChat* _"your_telegram_chat_id"_ - Set telegram chat
* */reg* _"code"_ - To link your telegram profile and minecraft nickname
* */del_tg* - To unlink your telegram profile
* */SetReg* _"on/off"_ - required/non-required [registration](#registration)

