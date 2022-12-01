import 'package:flutter/material.dart';
import 'package:plaza_audiovisual_interfaz/pages/login_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/register_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/home_page.dart';
import 'package:plaza_audiovisual_interfaz/widgets/search_user.dart';
import 'package:plaza_audiovisual_interfaz/widgets/user_profile.dart';
import 'package:plaza_audiovisual_interfaz/widgets/user_profile_form.dart';

import '../widgets/create_media.dart';

class NavBar extends StatefulWidget {
  static const routeName = 'navBar';
  const NavBar({super.key});
  @override
  State<NavBar> createState() => _NavBar();

}


class _NavBar extends State<NavBar> {

  int _indexTap = 0;

  final List<Widget> _widgetsChildren = [
    HomePage(),
    SearchUser(),
    CreateMedia(),
    const UserProfile(),
  ];

  void _onTapTapped(int index) {
    setState(() {
      _indexTap = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: _widgetsChildren.elementAt(_indexTap),
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Home',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.search),
            label: 'Search',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.add),
            label: 'Add',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person),
            label: 'Person',
          ),
        ],
        currentIndex: _indexTap,
        selectedItemColor: Colors.deepPurple,
        unselectedItemColor: const Color.fromARGB(255, 92, 79, 219),
        onTap: _onTapTapped,
      ),
    );
  }
}