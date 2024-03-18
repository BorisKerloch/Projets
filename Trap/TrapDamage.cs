using System.Collections;
using UnityEngine;

public class TrapDamage : MonoBehaviour
{
    [SerializeField] private bool isActivated = true;
    [SerializeField] public int damageAmount = 10;

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.tag == "Player" && isActivated)
        {   
            isActivated = false;
            StartCoroutine(ActiveTrap(collision));  
        }
    }

    private IEnumerator ActiveTrap (Collider2D collision) {
        Health playerHealth = collision.gameObject.GetComponent<Health>();
        if (playerHealth != null)
        {
            playerHealth.TakeDamage(damageAmount);
            yield return new WaitForSecondsRealtime(2);
        }
        isActivated = true;
    }

    public bool isTrapActivated  () {
        return isActivated;
    }
}
