import 'package:flutter/material.dart';

import '../utils/responsive.dart';
import 'icon_container.dart';

class CardUser extends StatelessWidget{

  final String nombreUser;
  final String detail;
  final String pathImage;
  const CardUser(this.nombreUser, this.detail,this.pathImage, {super.key});

  @override
  Widget build(BuildContext context) {
    final Responsive responsive = Responsive.of(context);
    final cardImage = Container(
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          boxShadow: const [
            BoxShadow(
                blurRadius: 16,
                color: Colors.black26,
                offset: Offset(0, 10)
            )
          ]
      ),
      child: Align(
        alignment: Alignment.center,
        child: Image.network(
          pathImage,
        ),
      ),
    );
    final cardDetail =  Container(
      width: double.maxFinite,
      margin: EdgeInsets.only(top: responsive.hp(40), bottom: responsive.hp(8)),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(9),
          boxShadow: const [
            BoxShadow(
                blurRadius: 16,
                color: Colors.black26,
                offset: Offset(0, 10)
            )
          ],
          color: Colors.white
      ),
        child: Stack(
          children: <Widget>[

            Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Text(
                      nombreUser,
                      style: const TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold
                      ),
                    ),
                  ],
                ),
                SizedBox(height: responsive.dp(2)),
                Align(
                  alignment: Alignment.bottomLeft,
                  child: Text(
                    detail,
                    style: const TextStyle(
                        color: Colors.black45,
                        fontSize: 18,
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
    );

    return Container(
      child: Stack(
        children: <Widget>[
          cardImage,
          const FloatingActionButton(
              onPressed: null,
              mini: true,
              backgroundColor: Color.fromRGBO(102, 216, 105, 1),
              child: Icon(Icons.favorite)
          ),
          cardDetail,
        ],
      ),
    );
  }
}