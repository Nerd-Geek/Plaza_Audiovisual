import 'package:flutter/material.dart';
import 'package:plaza_audiovisual_interfaz/model/user.dart';
import 'package:plaza_audiovisual_interfaz/widgets/card_user.dart';

import 'user_profile_search.dart';

class SearchUserDelegate extends SearchDelegate<User> {
  final List<User> users;

  List<User> _filer = [];

  SearchUserDelegate(this.users);

  @override
  List<Widget>? buildActions(BuildContext context) {
    return [
      IconButton(
          onPressed: () {
            query = "";
          },
          icon: const Icon(Icons.close)
      )
    ];
  }

  @override
  Widget? buildLeading(BuildContext context) {
    return IconButton(
        onPressed: () {
          close(context, User());
        },
        icon: const Icon(Icons.arrow_back)
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    return ListView.builder(
        itemCount: _filer.length,
        itemBuilder: (_, index) {
          return UserProfileSearch(
              user: _filer[index],
          );
        }
    );
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    _filer = users.where((user) {
      return user.username!.value.toLowerCase().contains(query.trim().toLowerCase());
    }).toList();
    return ListView.builder(
      itemCount: _filer.length,
        itemBuilder: (_, index) {
          return ListTile(
            title: Text(_filer[index].username!.value),
          );
        }
    );
  }

}