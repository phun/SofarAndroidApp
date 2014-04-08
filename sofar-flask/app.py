from flask import Flask, request, Response, jsonify, render_template, flash, redirect, g, url_for, session, abort, json
from MySQLdb import connect
app = Flask(__name__)
app.debug = True

import json, os, time

ADMINS = ['lucid@mit.edu']
if app.debug:
    import logging
    from logging.handlers import SMTPHandler
    mail_handler = SMTPHandler('127.0.0.1',
                               'server-error@example.com',
                               ADMINS, 'YourApplication Failed')
    mail_handler.setLevel(logging.ERROR)
    app.logger.addHandler(mail_handler)
    

def get_db():
    """Opens a new database connection if there is none yet for the
    current application context.
    """
    if not hasattr(g, 'db'):
        g.db = connect(host='sql.mit.edu', db='lucid+sofar', user='lucid', passwd='KO01BAHjiKGkZ')
    return g.db
    
@app.teardown_appcontext
def close_db(error):
    """Closes the database again at the end of the request."""
    if hasattr(g, 'db'):
        g.db.close()
        

@app.route('/', methods=['GET', 'POST'])
def index():
	return '<html><head></head><body>say HAY miss carter</body></html>'
	
@app.route('/users/<username>/profile', methods=['GET'])
def show_profile(username):
	c = get_db().cursor()
	if not c.execute("""SELECT json FROM profiles WHERE username=%s""", username):
		return '<html><head></head><body>User not found</body></html>', 404
	json = c.fetchone()[0]
	return app.response_class(json, indent=None if request.is_xhr else 2, mimetype='application/json')
	
@app.route('/users/<username>/profile', methods=['POST'])
def update_profile(username):
	c = get_db().cursor()
	old_json = {}
	if c.execute("""SELECT json FROM profiles WHERE username=%s""", username):
		old_json = json.loads(c.fetchone()[0])
	new_json = json.loads(flask.request.data)
	
	for k,v in new_json.iteritems():
		old_json[k] = v
	c.execute("REPLACE INTO profiles SET json=%s WHERE username=%s", (json.dumps(old_json), username))
	return '<html><head></head><body>User updated</body></html>'
		
@app.route('/users/<username>/profile', methods=['DELETE'])
def delete_profile(username):
	pass