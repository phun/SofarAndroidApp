from flask import Flask, request, Response, jsonify, render_template, flash, redirect, g, url_for, session
app = Flask(__name__)
app.debug = True

import json, os, time


@app.route('/', methods=['GET', 'POST'])
def index():
        return '<html><head></head><body>Hey</body></html>'



