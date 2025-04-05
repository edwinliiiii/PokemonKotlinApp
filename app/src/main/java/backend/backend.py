
# A very simple Flask Hello World app for you to get started with...

from flask import Flask, request, jsonify
import random, json

file_name = "./gen1.json"

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello from Flask!'

@app.route('/getPokemon', methods=["GET"])
def get_pokemon():
    try:
        page = int(request.args.get("page", 1))
    except ValueError:
        return jsonify({"error": "Invalid page number"}), 400
    pokemon_per_page = 25
    start = (page - 1) * pokemon_per_page + 1
    end = page * pokemon_per_page
    pokemon_list = []
    try:
        with open(file_name, "r", encoding="utf-8") as file:
            data = json.load(file)
            for pokemon in data:
                if pokemon["id"] >= start and pokemon["id"] <= end:
                    pokemon_list.append(pokemon)
    except FileNotFoundError:
        return(f"Error: The file '{file_name}' was not found.")
    except json.JSONDecodeError:
        return(f"Error: The file '{file_name}' contains invalid JSON.")
    return jsonify({
        "page": page,
        "pokemon": pokemon_list
    })

@app.route('/getRandomPokemon', methods=["GET"])
def get_random_pokemon():
    randy = random.randint(1, 150)
    random_pokemon = None
    try:
        with open(file_name, "r", encoding="utf-8") as file:
            data = json.load(file)
            for pokemon in data:
                if pokemon["id"] == randy:
                    random_pokemon = pokemon
    except FileNotFoundError:
        return(f"Error: The file '{file_name}' was not found.")
    except json.JSONDecodeError:
        return(f"Error: The file '{file_name}' contains invalid JSON.")
    return jsonify({
        "pokemon": random_pokemon
    })


